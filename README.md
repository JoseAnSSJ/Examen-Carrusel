# Prueba Técnica Carrusel

Aplicación desarrollada como parte de la prueba técnica para la posición de **Android Developer**. La app consume la API de https://devsapihub.com/ para listar películas en formato de carrusel con diferentes secciones. Además de mostrar el detalle de una película seleccionada junto a un video

---

## Capturas de pantalla


<img width="377" height="821" alt="image" src="https://github.com/user-attachments/assets/6cdac3d6-24ae-49f6-ada4-c206cf5bae8d" />

<img width="387" height="827" alt="image" src="https://github.com/user-attachments/assets/74fd17cd-2ebf-4efd-97f4-8ef709e9b662" />

<img width="385" height="825" alt="image" src="https://github.com/user-attachments/assets/86ef3d7a-4a0c-4b59-b1de-c77b18d21ba6" />

<img width="385" height="826" alt="image" src="https://github.com/user-attachments/assets/ac1a66f9-c815-460f-9313-9df16a7f9fdc" />


---

## Arquitectura y Decisiones de Diseño

El proyecto está estructurado siguiendo **Clean Architecture** orientada a características como el consumo de APIs, el manejo de estados y el patrón **MVVM (Model-View-ViewModel)**.

Capa de Dominio (domain): Contiene las reglas de negocio puras en Kotlin sin dependencias del framework de Android. Incluye las entidades del modelo de negocio, los casos de uso (UseCases) para acciones específicas y las interfaces/contratos que definen las operaciones de datos.

Capa de Datos (data): Implementa los contratos definidos en la capa de dominio. Maneja las fuentes de datos remotas (API REST mediante Retrofit), la lógica de los repositorios y los mapeadores (Mappers) que convierten las respuestas de red (DTOs) a modelos de dominio.

Capa de Presentación (presentation): Gestiona la interfaz de usuario construida de forma declarativa con Jetpack Compose. Los ViewModels procesan los eventos de usuario y exponen el estado de la UI (UiState) mediante StateFlow.

Módulo Base (core): Agrupa utilidades compartidas y clases envoltorio de estado, como la sellada Resource (Loading, Success, Error).

### ¿Por qué estas decisiones?
* **Jetpack Compose**: Se utilizó por ser el estándar actual de desarrollo nativo en Android.
* **Flow**: Para manejo seguro de estados en el ciclo de vida.
* **Inyección de Dependencias**: [Hilt] elegido para facilitar la testabilidad de los ViewModels y Repositories.

---

## Stack Tecnológico

* **Lenguaje:** 100% Kotlin
* **UI:** Jetpack Compose + Material 3
* **Arquitectura:** MVVM + Clean Architecture
* **Inyección de Dependencias:** [Hilt]
* **Networking:** Retrofit2
* **Asincronía:** Kotlin Coroutines & Flow
* **Testing:** JUnit, Mockk y CorrutinesTest

---

## Pruebas (Testing)

El proyecto cuenta con cobertura de pruebas en:
1. **Pruebas Unitarias de Mappers**: Validación del correcto funcionamiento.
2. **Pruebas Unitarias de Repository**: Validación de los estados.
3. **Pruebas Unitarias de Utils**: Validación de la extensión agregada.

---

## Cómo compilar y ejecutar

1. Clonar el repositorio:
   \`\`\`bash
   git clone https://github.com/JoseAnSSJ/Examen-Carrusel.git
   \`\`\`
2. Abrir **Android Studio**.
3. Sincronizar el proyecto con los archivos de Gradle (`Sync Project with Gradle Files`).
4. Ejecutar en un emulador o dispositivo físico con **API 26 o superior**.

---

## Mejoras pendientes

Si hubiera tenido más tiempo para esta prueba, habría implementado:
* **Persistencia de datos** con Agregar persistencia de datos, para trabajar offline.
* **Temas** Agregar la parte de temas oscuro/claro.
* **Agregar animaciones** para la pantalla principal tomando como base (https://www.sinasamaki.com/).
* **SplashView** al inicio de la aplicación.
* **Filtros** Usar la mayoría de filtros que me otorga la API.
* **Agregar más test** Completar la parte de testing.

## Autor
José Antonio García Rodíguez

Desarrollado como prueba técnica Android para Tv Azteca.
