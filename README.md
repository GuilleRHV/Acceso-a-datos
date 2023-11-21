# Acceso-a-datos
- int sentancia1 = sentencia.executeUpdate("sentencia update")
- int sentancia2 = sentencia.executeUpdate("sentencia update")
- int sentancia3 = sentencia.executeUpdate("sentencia update")
conexion.commit
int filas actualizadas = sentencia1+sentencia2+sentencia3


//
string sentenciaactualizar = update empleado set salario = salario * ? where codigo_departamento = ?
PreparedStatement sentencia = conexion.prepareStatement(sentenciaactualizar)
int[] numeDepart = {2,4,6}
doubles[] aumento = {1.01,1.02,1.03}
int empleados = 0;
for (int i=0;i<numDepart.lenght(),i++){
   sentencia.setString(2, numDept[i]);
   sentencia.setDouble(1,aumento[i]);
   empleados = empleados + sentencia.executeUpdate();
}
