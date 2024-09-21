import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.Scanner

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val scanner = Scanner(System.`in`)
    var ubicacioArxiu = "Fitxers/"
    var nomArxiu = scanner.next()
    ubicacioArxiu += nomArxiu
    "hola"

    val llistaLlibres = llegeixArxiu(ubicacioArxiu)



    println("Quina operació vols realitzar?")
    println(" 1 - Separa strings per //")
    var opcio = scanner.nextInt()
    while (opcio != 0)
    {
        when(opcio){
        }
        opcio = scanner.nextInt()
        println("Quina operació vols realitzar?")

    }




    //llibresList.forEach { llibres -> println(llibres.toString())}
}
fun separaString(frase : String) : MutableList<String>{
    val llistaNomsOEntitats = mutableListOf<String>()
    val campsNoms = frase.split("//")
    campsNoms.forEach{

        camp ->
        if (camp != "")
            llistaNomsOEntitats.add(camp)

    }
    return llistaNomsOEntitats
}

fun converteixALlibre(linea : String) : Llibre{
    val campsGenerals = linea.split(";")
    val llistaNoms = separaString(campsGenerals[1])
    val llistaEntitats = separaString(campsGenerals[2])
    val llibre = Llibre(campsGenerals[0], llistaNoms, llistaEntitats, campsGenerals[3],
        campsGenerals[4],
        campsGenerals[5],
        campsGenerals[6],
        campsGenerals[7],
        campsGenerals[8],
        campsGenerals[9],
        campsGenerals[10],
        campsGenerals[11],
        campsGenerals[12],
        campsGenerals[13],
        campsGenerals[14],
    )

    return  llibre
}

fun llegeixArxiu(arxiu : String) : MutableList<Llibre>{
    val inputStream: InputStream = File(arxiu).inputStream()
    var llibresList = mutableListOf<Llibre>()
    inputStream.bufferedReader().forEachLine {
        llibresList.add(converteixALlibre(it))
    }
    return llibresList
}

fun desaCsv(arxiu: String, llistaLlibres : MutableList<Llibre>){

    val writer = File(arxiu).outputStream().bufferedWriter()
    llistaLlibres.forEach{
        writer.write(it.Titol)
    }

}

class Llibre(
    var IdBNE : String,
    var AutorPersones : MutableList<String>,
    var AutorEntitats : MutableList<String> = mutableListOf(""),
    var Titol : String = "",
    var Descripcio : String = "",
    var Genere : String = "",
    var DipositLegal : String = "",
    var Pais : String = "",
    var Idioma : String = "",
    var VersioDigital : String = "",
    var TextOCR : String = "",
    var ISBN : String = "",
    var Tema : String = "",
    var Editorial : String = "",
    var LlocPublicacio : String = "",
)
