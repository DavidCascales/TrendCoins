package com.trendCoins.utilities

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trendCoins.models.ArticuloCarrito
import com.trendCoins.models.Cliente

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

object Email {
    private val smtpConfig = Properties()
    private val mailSession : Session
    private val mailMessage : Message
    init{
        smtpConfig["mail.from"] = "proyectofingrado2024@gmail.com"
        smtpConfig["mail.fromtext"] = "TrendCoins"
        smtpConfig["mail.smtp.username"] = "proyectofingrado2024@gmail.com"
        smtpConfig["mail.smtp.password"] = "ukto iqrz yhri anej"
        smtpConfig["mail.smtp.host"] = "smtp.gmail.com"
        smtpConfig["mail.smtp.port"] = "587"
        smtpConfig["mail.smtp.auth"] = "true"
        smtpConfig["mail.smtp.starttls.enable"] = "true"

        mailSession = Session.getInstance(smtpConfig, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication? {
                return PasswordAuthentication(
                    smtpConfig.getProperty("mail.smtp.username"),
                    smtpConfig.getProperty("mail.smtp.password")
                )
            }
        })

        mailMessage = MimeMessage(mailSession)
        mailMessage.setFrom(
            InternetAddress(
                smtpConfig.getProperty("mail.from"),
                smtpConfig.getProperty("mail.fromtext")
            )
        )
    }
    private fun generateCode() : String{
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..6)
            .map { allowedChars.random() }
            .joinToString("")
    }
    fun sendEmailPassReset(cliente: Cliente) : String {
        val multipart: Multipart = MimeMultipart()
        val htmlPart = MimeBodyPart()
        val code = generateCode()
        CoroutineScope(Dispatchers.IO).launch {
            htmlPart.setText("A continuación le dejamos su código para cambiar la contraseña: <strong style=\"font-family: 'Courier New', monospace;\">${code}</strong>", "utf-8", "html")
            multipart.addBodyPart(htmlPart)
            mailMessage.setContent(multipart)

            setSubject("TrendCoins - Código restauración contraseña")
            setRecipient(cliente.correo)
            Transport.send(mailMessage)
        }
        return code
    }
    fun sendEmailCompraDetails(
        cliente:Cliente, listaArticulosCarrito: SnapshotStateList<ArticuloCarrito>?,
        totalCompra:Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val multipart: Multipart = MimeMultipart()
            val htmlPart = MimeBodyPart()
            htmlPart.setText(getBodyForCompraDetails(cliente, listaArticulosCarrito, totalCompra), "utf-8", "html")
            multipart.addBodyPart(htmlPart)
            mailMessage.setContent(multipart)

            setSubject("TrendCoins - Confirmación Compra")
            setRecipient(cliente.correo)
            Transport.send(mailMessage)
        }
    }

    private fun setSubject(subject:String) {
        mailMessage.subject = subject
    }

    private fun setRecipient(recipient:String) {
        val toEmailAddresses = InternetAddress.parse(recipient)
        mailMessage.setRecipients(Message.RecipientType.TO, toEmailAddresses)
    }
    private fun getBodyForCompraDetails(cliente:Cliente, listaArticulosCarrito: SnapshotStateList<ArticuloCarrito>?, totalCompra:Int) : String {
        val articulos = listaArticulosCarrito!!.joinToString(separator = "") {
            "<li><strong>Artículo:</strong> ${it.descripcion}, <strong>Precio:</strong> ${it.precio}, <strong>Cantidad:</strong> ${it.cantidad}</li>"
        }
        return """
            
            <h3>Detalles de su compra</h3>
            <p>Estimado/a ${cliente.nombre},</p>
            <p>A qui le dejamos un resumen de su compra:</p>
            <ul>
              $articulos
            </ul>
            <p>Total compra de ${totalCompra} puntos.</p>
        """
    }
}
