package com.kuby.util

import java.nio.file.Files
import java.nio.file.Paths

fun loadMailTemplate(
    ruta: String,
    variables: Map<String, String>
): String {
    val path = Paths.get(ruta)
    var contenido = Files.readString(path)
    for ((clave, valor) in variables) {
        contenido = contenido.replace("{{${clave}}}", valor)
    }

    return contenido
}