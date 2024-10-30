package com.kuby.util

import jakarta.mail.Authenticator
import jakarta.mail.Message
import jakarta.mail.PasswordAuthentication
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage

suspend fun enviarCorreoPersonalizado(
    destinatario: String,
    asunto: String,
    plantillaRuta: String,
    variables: Map<String, String>
) {

    val username = System.getenv("EMAIL")
    val password = System.getenv("EMAIL_PASS")

    val cuerpoHtml = loadMailTemplate(plantillaRuta, variables)

    val props = System.getProperties().apply {
        put("mail.smtp.host", "smtp.gmail.com")
        put("mail.smtp.port", "587")
        put("mail.smtp.auth", "true")
        put("mail.smtp.starttls.enable", "true")
    }

    val session = Session.getInstance(props, object : Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication(username, password)
        }
    })

    try {
        val message = MimeMessage(session).apply {
            setFrom(InternetAddress(username))
            setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario))
            subject = asunto
            setContent(cuerpoHtml, "text/html; charset=utf-8")
        }

        Transport.send(message)
        println("Correo enviado exitosamente")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

