import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.Scanner
import kotlin.concurrent.fixedRateTimer
import java.io.FileNotFoundException


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val scanner = Scanner(System.`in`)
    var ubBase = "Fitxers/"
    try {
        println("Digues el nom del arxiu que vols llegir.")
        var nom = scanner.next()
        var location = ubBase + nom
        val llistaLlibres = llegeixArxiu(location)
        var opcio: Int
        do {
            println("Quina operació vols realitzar?")
            println("1 - Desa el CSV")
            println("2 - Omple Llibre")
            println("3 - EliminaLlibre")
            println("4 - EliminaPos")
            println("5 - LlistaPaisos")
            println("6 - LlistaPais")
            println("7 - LlistaIdiomes")
            println("8 - LlistaIdioma")
            println("9 - LlistaLlibre")
            println("10 - LlistaPos")
            println("11 - LlistaRang")
            println("0 - Sortir")
            opcio = scanner.nextInt()

            when (opcio) {
                1 -> {
                    println("Nom del arxiu on vols guardar aquest csv?")
                    var nomSortida = scanner.next()
                    var arxiusAdesar = "Fitxers/$nomSortida"
                    desaCsv(arxiusAdesar, llistaLlibres)
                    println("CSV desat correctament a $arxiusAdesar")
                }
                2->{
                    val llibre = ompleLlibre(llistaLlibres)
                    println("S'ha afegit el llibre - ")
                    println(toStringDeLlibre(llibre))
                }
                3->{
                    val scanner = Scanner(System.`in`)
                    println("Digues el idBNE del llibre que vulguis eliminar")
                    val input = scanner.next()
                    eliminaLlibre(input, llistaLlibres)
                }
                4->{
                    val scanner = Scanner(System.`in`)
                    println("Digues la posicio del llibre que vulguis eliminar")
                    val input = scanner.nextInt()
                    eliminaPos(input, llistaLlibres)
                }
                5->{
                    println("Llistat de llibres per Pais")
                    llistaPaisos(llistaLlibres)
                }

                6->{
                    println("Escriu qualsevol d'aquests paisos i t'ensenyem els llibres d'aquest pais")
                    val paisos = diferentsPaisos(llistaLlibres)
                    paisos.forEach{
                        println(it)
                    }
                    println("Escriu ara el pais")
                    val scanner = Scanner(System.`in`)
                    val pais = scanner.next()
                    llistaPais(pais, llistaLlibres)
                }
                7->{
                    println("Llistat de llibres per Idiomes")
                    llistaIdiomes(llistaLlibres)
                }
                8->{
                    println("Escriu qualsevol d'aquests idiomes i t'ensenyarem els llibres amb aquest idioma")
                    val idiomes = diferentsIidomes(llistaLlibres)
                    idiomes.forEach{
                        println(it)
                    }
                    println("Escriu ara l'idioma")
                    val scanner = Scanner(System.`in`)
                    val idioma = scanner.next()
                    llistaIdioma(idioma, llistaLlibres)
                }
                9->{
                    println("Digues un IdBNE i t'ensenyem quin llibre coincideix si es que hi ha algun")
                    val scanner = Scanner(System.`in`)
                    val input = scanner.next()
                    llistaIdBNE(input, llistaLlibres)
                }
                10->{
                    println("Digues una posicio i t'enseyem quin llibre coincideix si hi ha algun")
                    val scanner = Scanner(System.`in`)
                    val input = scanner.nextInt()
                    llistaIndex(input, llistaLlibres)
                }
                11->{
                    println("Digues 1 num")
                    val scanner = Scanner(System.`in`)
                    val num01 = scanner.nextInt()
                    println("Digues 1 altre num")
                    val num02 = scanner.nextInt()
                    llistaRangs(num01, num02, llistaLlibres)
                }
                0 -> println("Sortint del programa...")
                else -> println("Opció no vàlida")
            }
        } while (opcio != 0)
    }
    catch (e: FileNotFoundException){
        println(e.message)
    }
}

fun llistaRangs(num01: Int, num02: Int, llistaLlibres: MutableList<Llibre>) {
    if (num01 >= 0 && num02 < llistaLlibres.size && num01 <= num02) {
        val subllista = llistaLlibres.subList(num01, num02 + 1)
        if (subllista.isEmpty()) {
            println("No hi ha llibres entre aquestes posicions.")
        } else {
            println("Llistant llibres entre les posicions $num01 i $num02:")
            subllista.forEach { llibre ->
                println(toStringDeLlibre(llibre))
            }
        }
    } else {
        println("Les posicions introduïdes no són vàlides.")
    }
}

fun llistaIndex(input: Int, llistaLlibres: MutableList<Llibre>) {
    // Comprovem si l'índex està dins del rang vàlid
    if (input in 0 until llistaLlibres.size) {
        val llibreAct = llistaLlibres[input]
        println(toStringDeLlibre(llibreAct))
    } else {
        println("No hi ha cap llibre en aquesta posició.")
    }
}

fun llistaIdBNE(input: String?, llistaLlibres: MutableList<Llibre>) {

    var trobat = false
    var i = 1
    var llibreAct : Llibre
    while (!trobat && i < llistaLlibres.size)
    {
        llibreAct = llistaLlibres[i]
        if (llibreAct.IdBNE == input)
        {
            println("Llibre amb el IdBNE $input TROBAT")
            println("Ensenyant les dades......")
            println(toStringDeLlibre(llibreAct))
            trobat = true
        }
        i++
    }
    if (!trobat)
        println("Llibre amb el IdBNE $input NO TROBAT")
}

fun llistaIdioma(idioma: String?, llistaLlibres: MutableList<Llibre>) {
    val llistaLlibresPerIdioma = mutableListOf<String>()
    llistaLlibres.forEach{
        if (it.Idioma == idioma)
            llistaLlibresPerIdioma.add(it.Titol)
    }
    if (llistaLlibresPerIdioma.isEmpty())
        println("No hi ha ningun llibre amb l'idioma $idioma")
    else
    {
        println("Llibres amb l'idioma $idioma")
        llistaLlibresPerIdioma.forEach{
            println(it)
        }
    }
}

fun diferentsIidomes(llistaLlibres: MutableList<Llibre>) : MutableList<String>{

    val llistaIdiomes = mutableListOf<String>()
    llistaLlibres.forEach{
        if (!llistaIdiomes.contains(it.Idioma))
            llistaIdiomes.add(it.Idioma)
    }
    return llistaIdiomes
}



fun llistaIdiomes(llistaLlibres: MutableList<Llibre>) {
    val llistaIdiomes = diferentsIidomes(llistaLlibres)
    llistaIdiomes.forEach{
        idioma->
        println("\nLlista de Llibres fets amb el idioma $idioma \n")
        llistaLlibres.forEach{
            llibre->
            if (llibre.Idioma == idioma)
                println(llibre.Titol)
        }
    }
}



fun diferentsPaisos(llistaLlibres: MutableList<Llibre>) : MutableList<String>{

    val diferentsPaisos = mutableListOf<String>()
    llistaLlibres.forEach{
        if (!diferentsPaisos.contains(it.Pais) && it.Pais != "")
            diferentsPaisos.add(it.Pais)
    }
    return diferentsPaisos
}

fun llistaPais(pais: String, llistaLlibres: MutableList<Llibre>) {
    var llistaLlibresPerPais = diferentsPaisos(llistaLlibres)
    println("Llista de llibres fets al pais $pais\n")
    if (llistaLlibresPerPais.isEmpty())
        println("No hi ha ningun llibre per el pais $pais, o has fet un input erroni")
    else {
        llistaLlibresPerPais.forEach {
            println(it)
        }
    }
}

fun llistaPaisos(llistaLlibres: MutableList<Llibre>) {
    val diferentsPaisos = diferentsPaisos(llistaLlibres)
    diferentsPaisos.forEach{
        println("\nLlista de llibres fets al Pais : $it\n")
        val pais = it
        llistaLlibres.forEach{
            if (it.Pais == pais)
                println(it.Titol)
        }
    }
}

fun eliminaPos(pos : Int, llistaLlibres: MutableList<Llibre>) {

    var i = 0
    var llibre = llistaLlibres[i]
    var trobat = false
    while (i < llistaLlibres.size && !trobat)
    {
        if (i == pos)
        {
            llistaLlibres.removeAt(i)
            println("El llibre en pos $i ha estat eliminat")
            println("Llibre Eliminat")
            println(toStringDeLlibre(llibre))
            trobat = true
        }
        else{
            i++
            llibre = llistaLlibres[i]
        }

    }
    if (!trobat)
        println("La pos $i no existeix en el csv")

}

fun eliminaLlibre(input : String, llistaLlibres: MutableList<Llibre>) {

    var llibreEliminat : Llibre
    var trobat = false
    var i = 1
    while (!trobat && llistaLlibres.size > i)
    {
        val llibre = llistaLlibres[i]
        if (llibre.IdBNE == input)
        {
            llibreEliminat = llibre
            llistaLlibres.remove(llibre)
            println("S'ha eliminat el llibre \n${toStringDeLlibre(llibreEliminat)}")
            trobat = true
        }
        i++
    }
    if (!trobat)
        println("No s'ha eliminat ningun llibre ja que el IDBNE no concorda amb ningun del csv")
}

fun toStringDeLlibre(llibre: Llibre) : String{
    return "${llibre.IdBNE}, ${llibre.AutorPersones}, ${llibre.AutorEntitats}, " +
            "${llibre.Titol}, ${llibre.Descripcio}, ${llibre.Genere}, ${llibre.DipositLegal}, " +
            "${llibre.Pais}, ${llibre.Idioma}, ${llibre.VersioDigital}, ${llibre.TextOCR}, " +
            "${llibre.ISBN}, ${llibre.Tema}, ${llibre.Editorial}, ${llibre.LlocPublicacio}"
}

fun ompleLlibre(llistaLlibres: MutableList<Llibre>) : Llibre {
    val scanner = Scanner(System.`in`)
    println("Estigues atent tens que escriure el llibre que vols entrar amb una separació de ';' per camps")
    println("IdBNE, AUTOR PERSONES (En cas de varies separa cada persona per // ), AUTOR ENTITATS (En cas de varies separa cada persona per // ), TITOL, DESCRIPCIO, GENERE, DPOSITLEGAL, PAIS, IDIOMA, VERSIODIGITAL, TEXTOCR, ISBN, TEMA, EDITORIAL, LLOCPUBLICAICO")
    val frase = scanner.nextLine()
    return altaLlibre(frase, llistaLlibres)
}

fun altaLlibre(frase: String, llistaLlibres: MutableList<Llibre>) : Llibre{

    var llibre = converteixALlibre(frase)
    var trobat = false

    var i = 0
    while (!trobat && i < llistaLlibres.size){
        val llibreATrobat = llistaLlibres[i]
        if (llibre.IdBNE == llibreATrobat.IdBNE)
        {
            llistaLlibres[i] = llibre
            trobat = true
        }
        i++
    }
    if (!trobat)
        llistaLlibres.add(llibre)
    return  llibre
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
        val persones = it.AutorPersones.joinToString(" // ")
        val entitats = it.AutorEntitats.joinToString(" // ")
        writer.write("${it.IdBNE}, $persones, $entitats, ${it.Titol}, ${it.Descripcio}, ${it.Genere}, ${it.DipositLegal}, ${it.Pais}, ${it.Idioma}, ${it.VersioDigital}, ${it.TextOCR}, ${it.ISBN}, ${it.Tema}, ${it.Editorial}, ${it.LlocPublicacio}")
        writer.newLine()
    }
    writer.flush()
    writer.close()

}


class Llibre(
    var IdBNE : String = "",
    var AutorPersones : MutableList<String> = mutableListOf(""),
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
