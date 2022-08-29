# Library for Android

Mit diesem Projekt sollen alle zu transpilierenden Funktionen als Library entwickelt werden. Der Sinn besteht darin, alle häufig genutzten Funktionen der jeweiligen Features zu kapseln und über eine einheitliche API anzubieten, die in einem späteren Schritt durch den Transpiler leichter transpiliert werden kann.

## Installation

Dieses Projekt ist ein normales Android Studio Projekt. Das Modul "library" ist für den Library-Code gedacht und besitzt keine Oberfläche.
Um eine Oberfläche zum Testen zu haben muss ein neues Modul "app" erstellt werden und die "library" als Dependency markiert werden.
Da die Oberfläche nur für das Testen gedacht ist wird diese nicht in das Repository gepusht!

Nach dem Clone ist keine Verknüpfung zu dem Modul "library" vorhanden ob wohl der Code da ist.
Dazu erstellt im Root-Ordner eine neue Datei "settings.gradle" und fügt die Zeile "include ':library'" hinzu.
(Tipp: Oben rechts im Seitenmenü "Project" kann man auswählen, was im Dateibaum angezeigt werden soll. Hier könnt ihr mit "Project" den Root-Ordner sehen)
Anschließend "Sync Now" drücken und das Modul ist da.

Um eine Oberfläche hinzuzufügen navigiert in File > Project Structure > Modules. Anschließend das "+" Symbol unter dem Wort "Modules" klicken.
Als Modultyp wählt ihr "Phone & Tablet Module" aus und geht zur Benennung weiter.
Der Name muss "app" (kleingeschrieben) sein.
Im Feld "Module name" sollte nun auch "app" stehen (Weil die GitIgnore explizit diesen Ordner ignoriert).
Die Sprache bleibt Kotlin und die Version API 26. Dann kann mit jeder Art von Activity gestartet werden, ich empfehle die "empty activity".

Abschließend muss das neue Module "app" noch unsere "library" verwenden.
Dazu navigiert in File > Project Structure > Dependencies und klickt auf das neue Module "app".
Rechts sieht man alle Dependencies dieses einen Modules.
Unter dem Wort "Declared Dependencies" gibt es wieder ein "+" was geklickt werden muss und "Module Dependency" ausgewählt werden.
Jetzt müsste eine Listenansicht aufgehen und das Modul "library" anklickbar sein. Anschließend "ok" und dann "apply" drücken.
Dann sollten alle Klassen aus dem Module "library" mittels "import de.thm.tp.library..." importierbar sein.

## Exportieren

Wenn das Modul "library" fertig ist, kann mit dem Befehl "gradlew assemble" der BuildProzess angestoßen werden.
Im Ordner library > build > outputs > aar entsteht dann die library-release.aar. Die funktioniert genauso wie eine .jar.
Sie kann über den Ordner "libs" jedem anderen Modul hinzugefügt werden und anschließend über File > Project Structure als Dependency hinzugefügt werden.