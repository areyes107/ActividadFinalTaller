-- procedimientos para maestros 

-- agregar 
Delimiter $$
create procedure sp_AgregarMaestro(IN teacherName varchar(150), IN teacherLastNames varchar(150), IN bornDate date, IN sex varchar(1), IN title varchar(150), IN address varchar(250), IN phoneNumber varchar(9))
	Begin 
		insert into Maestros(nombreMaestro, apellidosMaestro, fechaNacimiento, sexo, titulo, direccion, telefono)
			values(teacherName, teacherLastNames, bornDate, sex, title, address, phoneNumber);
	End$$
Delimiter ;


-- listar
Delimiter $$
	create procedure sp_ListarMaestros()
    Begin 
		select * from Maestros;
    End$$
Delimiter ;

-- eliminar 
Delimiter $$
create procedure sp_EliminarMaestro(IN teacherCode int)
	Begin 
		delete from Maestros where codigoMaestro = teacherCode;
    End$$
Delimiter ;


-- actualizar 
Delimiter $$
create procedure sp_ActualizarMaestro(IN teacherCode int, IN teacherName varchar(150), IN teacherLastNames varchar(150), IN bornDate date, IN sex varchar(1), IN title varchar(150), IN address varchar(250), IN phoneNumber varchar(9))
	Begin 
		update Maestros
			set nombreMaestro = teacherName, apellidosMaestro = teacherLastNames, fechaNacimiento = bornDate, sexo = sex, titulo = title, direccion = address, telefono = phoneNumber	 
				where codigoMaestro =teacherCode;
    End$$
Delimiter ;

-- buscar
Delimiter $$
create procedure sp_BuscarMaestro (IN teacherCode int)
	Begin 
		select * from Maestros 
			where codigoMaestro = teacherCode;
    End$$
Delimiter ; 



-- funcion 
    
Delimiter $$
create function fn_CalcularNotaTotal(notaAcumulativa decimal(6,2), notaEvaluacion decimal(6,2))returns decimal(6,2)
reads sql data deterministic
	Begin 
		declare notaF decimal(6,2);
		declare zona decimal(6,2);
		declare examen decimal(6,2);
		
		set zona = (notaAcumulativa*0.4);
		set examen = (notaEvaluacion*0.6);
		
		set notaF = zona+examen;
        
		return notaF;
    End$$
Delimiter ;

select fn_CalcularNotaTotal(100,100) as NotaFinal;




