> [!NOTE]
> Si da errores al importar proyecto

Haz clic derecho en el proyecto en el Explorador de Proyectos.
Selecciona "Propiedades".
Navega a "Java Build Path" > "Libraries".
Deberías ver la entrada de la biblioteca del sistema Java (JRE System Library).
Si ves una etiqueta que dice "unbound", significa que el proyecto no está vinculado a un JRE válido.
Haz clic en "Editar...".
Selecciona la opción "Ejecutar entorno de ejecución de Java JRE".
Elige un JRE instalado en tu sistema.
Haz clic en "Finalizar" para vincular el proyecto al JRE seleccionado.
