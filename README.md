# ☁️ ClimateComparer

**Deine smarte Wetter-App mit Favoritenfunktion, Standortwetter und stilvollem UI – gebaut mit Jetpack Compose, Clean Architecture und ganz viel ☀️🌧️❄️.**

---

## 📚 Inhaltsverzeichnis

1. [🚀 Ziel der App](#-ziel-der-app)
2. [📊 UML-Diagramm](#-uml-diagramm)
3. [🖼️ Features](#️-features)
4. [🏗️ Architektur](#️-architektur)
5. [🔌 API & Datenquellen](#-api--datenquellen)
6. [🧪 Testing](#-testing)
7. [🛠️ Technologien](#️-technologien)

---

## 🚀 Ziel der App

ClimateComparer soll eine minimalistische und intuitive Wetter-App werden, die dir nicht nur das Wetter an deinem aktuellen Standort zeigt, sondern auch eine Vergleichsmöglichkeit über deine Lieblingsstädte bietet. Zusätzlich gibt’s eine Wetterradar-Ansicht, Stundenprognosen und eine elegante Benutzeroberfläche.

---

## 📊 UML-Diagramm

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
---

## 🖼️ Features

- 📍 Standortbasiertes aktuelles Wetter
- ❤️ Favoritenliste (Städte hinzufügen & verwalten)
- 🕒 Stunden- & Tagesprognosen
- 📊 UV-Index und Windgeschwindigkeit
- 🗺️ Wetterradar-Ansicht (Map)
- 🔎 Suchfunktion für Städte
- 💾 Lokale Datenhaltung (z. B. Room oder DataStore geplant)

---

## 🏗️ Architektur

Wir setzen auf eine klare Trennung von Verantwortlichkeiten mit Fokus auf **Clean Architecture**, **MVVM** und **Domain-Driven Design**:

---

## 🔌 API & Datenquellen

- 🌐 [OpenWeatherMap API](https://openweathermap.org/api) (für Wetter, Forecast, UV Index)
- 📦 Retrofit2 für API-Anbindung
- 🔐 API-Key wird lokal abgesichert via `.env` (nicht committed)

---

## 🧪 Testing

- ✅ Unit-Tests für UseCases
- 🧪 Mocked Repositories für ViewModel-Tests
- 🔍 Komponententests für Screens (geplant)

---

## 🛠️ Technologien

- 🧑‍💻 Kotlin
- 🧱 Jetpack Compose
- 🧪 Retrofit2
- 🔀 Coroutine Flow / StateFlow
- 🧩 Clean Architecture
- 📦 Hilt (oder Koin) für Dependency Injection
- 🔍 JUnit + MockK für Tests

---

> ✨ _“There’s no such thing as bad weather – only wrong apps.”_
