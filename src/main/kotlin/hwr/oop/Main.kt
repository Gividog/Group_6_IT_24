package hwr.oop
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.JTextField
import hwr.oop.data.MonsterRepository
import hwr.oop.init.MonsterInitializer

fun main() {
    // Hauptfenster erstellen
    val frame = JFrame("Kotlin Terminal")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.setSize(600, 400)

    // Textbereich für die "Konsole"
    val textArea = JTextArea()
    textArea.isEditable = false
    textArea.background = Color.BLACK
    textArea.foreground = Color.GREEN
    textArea.font = Font("Monospaced", Font.PLAIN, 14)

    // Eingabefeld
    val inputField = JTextField()
    inputField.background = Color.BLACK
    inputField.foreground = Color.WHITE
    inputField.font = Font("Monospaced", Font.PLAIN, 14)

    // Action: Wenn Enter gedrückt wird
    inputField.addActionListener {
        val command = inputField.text
        textArea.append("> $command\n")
        // Beispiel: Ausgabe eines Befehls
        if (command == "help") {
            textArea.append("Verfügbare Befehle: help, clear, exit\n")
        } else if (command == "clear") {
            textArea.text = ""
        } else if (command == "exit") {
            System.exit(0)
        } else {
            textArea.append("Unbekannter Befehl: $command\n")
        }
        inputField.text = ""
    }

    // Layout festlegen
    frame.layout = BorderLayout()
    frame.add(JScrollPane(textArea), BorderLayout.CENTER)
    frame.add(inputField, BorderLayout.SOUTH)

    // Fenster sichtbar machen
    frame.isVisible = true

    // MonsterRepository.loadFromFile() wenn wir unsere Monster später in einer Datei speichern

    MonsterInitializer.preload() // Nur wenn wir wirklich die vorkonfigurierten Monster nutzen wollen

    // // TODO Console erstellen (neues Terminal); CLI Interaktion dann hier

    // MonsterRepository.saveToFile() wenn wir unsere Monster später in einer Datei speichern
}
