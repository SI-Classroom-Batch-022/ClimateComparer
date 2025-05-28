# Climate Comparer

Eine moderne Android-Wetter-App, die mit Jetpack Compose entwickelt wurde und aktuelle Wetterdaten sowie Vorhersagen für verschiedene Städte bereitstellt.

## 👥 Entwicklerteam

Dieses Projekt wurde in nur **4 Tagen** von einem engagierten Team entwickelt:

- **Kim** 
- **Pascal** 
- **Tayfun** 

Ein beeindruckendes Beispiel für effiziente Teamarbeit und moderne Android-Entwicklung! 🚀

## 🌟 Features

### Hauptfunktionen
- **Aktuelle Wetterdaten**: Temperatur, Wetterzustand, Wind und UV-Index
- **Stündliche Vorhersage**: 24-Stunden-Wettervorhersage
- **Tägliche Vorhersage**: 7-, 14- oder 16-Tage-Prognose
- **Städtesuche**: Suche nach Städten weltweit
- **Favoriten**: Speicherung von Lieblingsstädten
- **Glasmorphismus UI**: Moderne, transparente Benutzeroberfläche
- **Tag/Nacht-Modi**: Verschiedene Hintergründe und Icons je nach Tageszeit

### Wetterdetails
- Windgeschwindigkeit und -richtung mit Kompass
- UV-Index mit farbkodierter Anzeige
- Niederschlagsmenge
- Wettericons abhängig von Tageszeit
- Interaktive Wetterdetail-Karten

## 🏗️ Architektur

Die App folgt modernen Android-Entwicklungsprinzipien:

### Clean Architecture
```
├── ui/                          # Presentation Layer
│   ├── detailmain/             # Wetter-Hauptscreen
│   ├── citylist/               # Städteliste und Favoriten
│   └── theme/                  # UI Theme und Styling
├── data/
│   ├── model/                  # Datenmodelle
│   ├── remote/                 # API-Services
│   ├── database/               # Room Database
│   └── repository/             # Repository Pattern
├── navigation/                 # Navigation Logic
└── di/                         # Dependency Injection
```

### Verwendete Technologien
- **Jetpack Compose**: Moderne UI-Entwicklung
- **MVVM Pattern**: Model-View-ViewModel Architektur
- **Koin**: Dependency Injection
- **Retrofit**: HTTP-Client für API-Aufrufe
- **Moshi**: JSON-Serialisierung
- **Room**: Lokale Datenbank für Favoriten
- **Coroutines & Flow**: Asynchrone Programmierung
- **Navigation Compose**: Navigation zwischen Screens

## 🌐 APIs

### Open-Meteo Weather API
- **Wetter-API**: `https://api.open-meteo.com/v1/`
- **Geocoding-API**: `https://geocoding-api.open-meteo.com/v1/`

Die App nutzt die kostenlose Open-Meteo API für:
- Aktuelle Wetterdaten
- Stündliche Vorhersagen
- Geocoding für Städtesuche

## 📱 Benutzeroberfläche

### Hauptscreen (DetailedMainScreen)
- Wetter-Header mit Stadt und aktueller Temperatur
- Stündliche Vorhersage-Sektion
- Detaillierte Wetterinformationen
- Tägliche Vorhersage mit wählbaren Zeiträumen
- Dynamische Hintergrundbilder je nach Wetter und Tageszeit

### Städteliste (CityListScreen)
- Suchfunktion für neue Städte
- Favoriten-Verwaltung
- Ein-Klick-Auswahl von Städten

### UI-Komponenten
- **SmallGlassmorphismCard**: Transparente Karten mit Glaseffekt
- **WeatherDetailCard**: Wetterdetails mit Icons
- **WindDirectionCompass**: Windrichtungsanzeige mit Kompass
- **HourlyWeatherCard**: Stündliche Wettervorschau
- **DailyWeatherCard**: Tägliche Wettervorhersage

## 🛠️ Setup und Installation

### Voraussetzungen
- Android Studio Hedgehog (2023.1.1) oder neuer
- Android SDK 26 (minimum) - 35 (target)
- Kotlin 1.9+
- Java 11

### Installation
1. Repository klonen:
```bash
git clone [repository-url]
cd climate-comparer
```

2. Projekt in Android Studio öffnen

3. Gradle Sync ausführen

4. App auf Gerät oder Emulator starten

### Abhängigkeiten
Die wichtigsten Abhängigkeiten sind in `app/build.gradle.kts` definiert:

```kotlin
// Core Android
implementation("androidx.core:core-ktx")
implementation("androidx.activity:activity-compose")

// Compose
implementation(platform("androidx.compose:compose-bom"))
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")
implementation("androidx.navigation:navigation-compose")

// Networking
implementation("com.squareup.retrofit2:retrofit")
implementation("com.squareup.retrofit2:converter-moshi")
implementation("com.squareup.moshi:moshi-kotlin")

// Dependency Injection
implementation(platform("io.insert-koin:koin-bom"))
implementation("io.insert-koin:koin-android")
implementation("io.insert-koin:koin-androidx-compose")

// Database
implementation("androidx.room:room-runtime")
implementation("androidx.room:room-ktx")
```

## 📂 Projektstruktur

### Datenmodelle
- **Weather**: Hauptwetter-Datenmodell
- **City**: Stadt mit Geolocation und Favoriten-Status
- **GeoLocation**: Geografische Koordinaten und Ortsinformationen
- **WeatherState**: Enum für Wetterzustände
- **HourlyWeather**: Stündliche Wetterdaten

### Repository Pattern
- **WeatherRepositoryInterface**: Wetter-Daten-Zugriff
- **GeoLocationRepositoryInterface**: Geolocation-Services
- **FavoriteCitiesRepositoryInterface**: Favoriten-Verwaltung

### ViewModels
- **WeatherViewModel**: Wetter-Daten und UI-State
- **NavigationViewModel**: App-Navigation und Favoriten
- **FavoriteCitiesViewModel**: Favoriten-Verwaltung

## 🎨 Design System

### Farbschema
- **Primary**: Purple-basierte Farbpalette
- **Surface**: Transparente Glasmorphismus-Effekte
- **Dynamic Colors**: Android 12+ Material You Support

### Typografie
- Material 3 Typography System
- Responsive Text-Größen
- Accessibility-optimiert

### Icons und Bilder
- Material Icons Extended
- Custom Weather Icons
- Dynamische Hintergrundbilder

## 🔧 Konfiguration

### Dependency Injection (Koin)
Module sind in `di/AppModule.kt` konfiguriert:
- API Services
- Repositories
- ViewModels
- Database

### Database
Room Database für Favoriten:
- **FavoriteCity**: Entity für gespeicherte Städte
- **FavoriteCitiesDao**: Data Access Object
- **GeoLocationConverter**: JSON-Konvertierung für komplexe Objekte

## 🚀 Deployment

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

### Proguard
Release-Builds verwenden Proguard für Code-Optimierung (konfiguriert in `proguard-rules.pro`).

## 🧪 Testing

### Unit Tests
```bash
./gradlew test
```

### Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

## 📱 Unterstützte Android-Versionen

- **Minimum SDK**: 26 (Android 8.0)
- **Target SDK**: 35 (Android 15)
- **Compile SDK**: 35

## 🤝 Mitwirkung

1. Fork des Projekts erstellen
2. Feature Branch erstellen (`git checkout -b feature/AmazingFeature`)
3. Änderungen committen (`git commit -m 'Add some AmazingFeature'`)
4. Branch pushen (`git push origin feature/AmazingFeature`)
5. Pull Request erstellen

## 📄 Lizenz

Dieses Projekt steht unter der MIT-Lizenz. Siehe `LICENSE` Datei für Details.

## 🆘 Support

Bei Fragen oder Problemen:
1. Issues auf GitHub erstellen
2. Dokumentation prüfen
3. Code-Kommentare beachten

## 🔄 Roadmap

### Geplante Features
- [ ] Wetter-Warnungen und Benachrichtigungen
- [ ] Widget-Support
- [ ] Erweiterte Wetterdiagramme
- [ ] Standort-basierte automatische Updates
- [ ] Mehrsprachige Unterstützung
- [ ] Dark/Light Theme Toggle
- [ ] Wettervergleich zwischen Städten

### Bekannte Probleme
- Einige Wettericons könnten bei seltenen Wetterzuständen fehlen
- Performance-Optimierung für große Städtelisten

---

**Entwickelt mit ❤️ und Jetpack Compose in nur 4 Tagen von Kim, Pascal und Tayfun**
