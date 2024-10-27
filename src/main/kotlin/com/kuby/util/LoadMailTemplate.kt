package com.kuby.util

import java.nio.file.Files
import java.nio.file.Paths

suspend fun LoadMailTemplate(
    ruta: String,
    variables: Map<String, String>
): String {
    val path = Paths.get(ruta)
    var contenido = Files.readString(path)

    // Reemplaza cada variable en el contenido
    for ((clave, valor) in variables) {
        contenido = contenido.replace("{{${clave}}}", valor)
    }

    return contenido
}