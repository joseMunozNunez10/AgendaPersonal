# Agenda Personal

"Agenda Personal" es una aplicación de Android multifuncional diseñada para ayudarte a organizar tu vida diaria. Combina un completo gestor de eventos con un potente sistema de seguimiento financiero, todo en una interfaz moderna, intuitiva y fácil de usar.

## ✨ Características Principales

-   **Gestión de Eventos:**
    -   Añade, edita y elimina eventos fácilmente.
    -   Visualiza tus eventos en un calendario interactivo.
    -   Establece recordatorios y notificaciones para no olvidar nada.

-   **Seguimiento Financiero:**
    -   Registra tus ingresos y gastos de forma rápida.
    -   Organiza tus transacciones con categorías personalizadas.
    -   El tipo de transacción (ingreso/gasto) se asigna automáticamente al elegir una categoría.
    -   Visualiza un resumen financiero con gráficos para entender mejor tus hábitos de consumo.

-   **Seguridad:**
    -   Protege tu información con autenticación biométrica (huella dactilar).

-   **Interfaz Moderna:**
    -   Diseño limpio y claro basado en Material Design 3.
    -   Soporte para tema claro y oscuro.

## 📸 Capturas de Pantalla (Preview)

*(Aquí puedes añadir capturas de pantalla de tu aplicación)*

| Pantalla Principal (Calendario) | Resumen Financiero |
| :-----------------------------: | :--------------------: |
| *(img/screenshot_main.png)*     | *(img/screenshot_finance.png)* |

| Añadir Transacción              | Inicio de Sesión (Biométrico) |
| :------------------------------: | :---------------------------: |
| *(img/screenshot_add_transaction.png)* | *(img/screenshot_login.png)*   |


## 🛠️ Tecnologías Utilizadas

-   **Lenguaje:** [Kotlin](https://kotlinlang.org/)
-   **Arquitectura:** MVVM (Model-View-ViewModel)
-   **Componentes de Jetpack:**
    -   **UI:** ViewBinding y Layouts XML.
    -   **Navegación:** Navigation Component para gestionar el flujo entre pantallas.
    -   **Base de Datos:** Room para la persistencia de datos local.
    -   **Ciclo de Vida:** ViewModel, LiveData y StateFlow para una gestión de datos consciente del ciclo de vida.
-   **Asincronía:** Kotlin Coroutines para operaciones en segundo plano.
-   **Inyección de Dependencias:** Hilt (Dagger) para desacoplar las dependencias.
-   **UI y Diseño:**
    -   [Material Design 3](https://m3.material.io/): Para los componentes de la interfaz.
    -   [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart): Para los gráficos financieros.
    -   [Material-Calendar-View](https://github.com/Applandeo/Material-Calendar-View): Para el calendario de eventos.
-   **Seguridad:**
    -   AndroidX Biometric & Security Crypto para el login seguro.

## 🚀 Cómo Empezar

Para compilar y ejecutar el proyecto, sigue estos pasos:

1.  **Clona el repositorio:**
    ```sh
    git clone https://URL-DE-TU-REPOSITORIO.git
    ```
2.  **Abre el proyecto en Android Studio.**
3.  **Sincroniza Gradle:** Espera a que Android Studio descargue todas las dependencias necesarias.
4.  **Ejecuta la aplicación:** Pulsa el botón `Run 'app'` para instalarla en un emulador o dispositivo físico.

¡Y listo!

---
*Este README fue generado con la ayuda de Gemini en Android Studio.*
