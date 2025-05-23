# ClimateComparer

## UML-Diagramm

```mermaid
classDiagram
    class ClimateComparer {
        -aktuellerStandort: Stadt
        -favoritenListe: FavoritenListe
        -hauptScreen: HauptScreen
        +starteApp()
        +wechsleStandort(stadt: Stadt)
        +aktualisiereWetter()
    }

    class Stadt {
        -name: String
        -koordinaten: Koordinaten
        -istFavorit: Boolean
        +getName(): String
        +getKoordinaten(): Koordinaten
        +setFavorit(status: Boolean)
    }

    class Koordinaten {
        -breitengrad: Double
        -längengrad: Double
        +getBreitengrad(): Double
        +getLängengrad(): Double
    }

    class FavoritenListe {
        -städte: List~Stadt~
        +hinzufügenStadt(stadt: Stadt)
        +entfernenStadt(stadt: Stadt)
        +getAlleStädte(): List~Stadt~
        +istFavorit(stadt: Stadt): Boolean
    }

    class HauptScreen {
        -aktuelleWetterdaten: Wetterdaten
        -zehnTageVorhersage: List~TagesWetter~
        -favoritenButton: FloatingActionButton
        +zeigeWetter(daten: Wetterdaten)
        +zeigeVorhersage(vorhersage: List~TagesWetter~)
        +aktualisiereAnzeige()
        +öffneFavoriten()
    }

    class FavoritenScreen {
        -favoritenListe: FavoritenListe
        +zeigeFavoriten()
        +stadtAuswählen(stadt: Stadt)
        +stadtHinzufügen(stadt: Stadt)
        +stadtEntfernen(stadt: Stadt)
        +zurückZumHauptScreen()
    }

    class FloatingActionButton {
        -position: String
        -icon: String
        +onClick()
    }

    class Wetterdaten {
        -stadt: Stadt
        -temperatur: Double
        -wetterZustand: WetterZustand
        -uvIndex: Integer
        -windgeschwindigkeit: Double
        -windrichtung: String
        -niederschlag: Double
        -zeitstempel: DateTime
        +getTemperatur(): Double
        +getWetterZustand(): WetterZustand
        +getUVIndex(): Integer
    }

    class TagesWetter {
        -datum: Date
        -maxTemperatur: Double
        -minTemperatur: Double
        -wetterZustand: WetterZustand
        -niederschlagswahrscheinlichkeit: Integer
        +getDatum(): Date
        +getMaxTemperatur(): Double
        +getMinTemperatur(): Double
    }

    class WetterZustand {
        <<enumeration>>
        SONNIG
        BEWÖLKT
        TEILWEISE_BEWÖLKT
        REGNERISCH
        STÜRMISCH
        SCHNEE
        NEBEL
    }

    class WetterService {
        +holeAktuelleWetterdaten(stadt: Stadt): Wetterdaten
        +holeZehnTageVorhersage(stadt: Stadt): List~TagesWetter~
        +holeWetterNachKoordinaten(koordinaten: Koordinaten): Wetterdaten
    }

    %% Beziehungen
    ClimateComparer "1" --> "1" FavoritenListe : verwaltet
    ClimateComparer "1" --> "1" HauptScreen : zeigt
    ClimateComparer "1" --> "1" FavoritenScreen : navigiert
    ClimateComparer "1" --> "0..1" Stadt : aktuellerStandort
    
    FavoritenListe "1" --> "*" Stadt : enthält
    Stadt "1" --> "1" Koordinaten : hat
    
    HauptScreen "1" --> "1" Wetterdaten : zeigt
    HauptScreen "1" --> "*" TagesWetter : zeigt
    HauptScreen "1" --> "1" FloatingActionButton : hat
    
    FavoritenScreen "1" --> "1" FavoritenListe : zeigt
    
    Wetterdaten "1" --> "1" Stadt : gehörtZu
    Wetterdaten "1" --> "1" WetterZustand : hat
    TagesWetter "1" --> "1" WetterZustand : hat
    
    WetterService ..> Wetterdaten : erstellt
    WetterService ..> TagesWetter : erstellt
    ClimateComparer --> WetterService : nutzt
```
