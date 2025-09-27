# 📒 Agenda Personal (Android App)

Aplicación Android desarrollada en **Kotlin** con arquitectura moderna y librerías de Jetpack.
El objetivo es gestionar una **agenda personal segura**, con almacenamiento local y autenticación biométrica.

---

## 🚀 Tecnologías principales

* **Kotlin** → Lenguaje principal.
* **Room (Jetpack)** → Persistencia local estructurada en SQLite.
* **Lifecycle + ViewModel** → Separación de lógica de UI, sobreviviendo cambios de configuración.
* **Kotlin Coroutines / Flow** → Operaciones en background sin bloquear la UI.
* **BiometricPrompt (androidx.biometric)** → Autenticación con huella o biometría del dispositivo.
* **EncryptedSharedPreferences (androidx.security-crypto)** → Almacenamiento seguro de credenciales o PIN.
* **Material Components** → Interfaz moderna y consistente.
* **ViewBinding** → Manipulación de vistas sin `findViewById`.

---

## Características principales

- Persistencia local con **Room Database**.
- Arquitectura **MVVM** para separar UI de la lógica de negocio.
- **Repository** como único punto de acceso a la base de datos.
- **ViewModel** con **StateFlow** para exponer datos reactivos a la UI.
- Operaciones asíncronas con **Coroutines**.
- Soporte para:
  - Categorías de eventos.
  - Prioridad (ALTA, MEDIA, BAJA).
  - Colores de identificación.
  - Etiquetas múltiples.
  - Eventos recurrentes con regla RRULE.
- Generación de ID único para cada evento con **UuidGenerator**.

## ⚡ Configuración de Gradle

Dependencias clave incluidas:

* Room (runtime, ktx, compiler con KSP)
* Lifecycle (ViewModel, LiveData, Runtime)
* Coroutines (core, android)
* Biometric
* Security-Crypto
* Material Design Components

---

## 🛠️ Instalación y ejecución

1. Clonar el repositorio:

   ```bash
   git clone https: https://github.com/joseMunozNunez10/AgendaPersonal
   cd agendapersona
   ```

2. Abrir en **Android Studio** (versión recomendada: Iguana+).

3. Sincronizar Gradle (`Sync Now`).

4. Ejecutar en un dispositivo físico o emulador con Android **>= 6.0 (API 23)**.

---

## 👨‍💻 Autor

**Jose Muñoz**
Desarrollador Full Stack Android & Python 
[LinkedIn](https://www.linkedin.com/in/jose-munoz-nunez/) | [GitHub](https://github.com/joseMunozNunez10)
