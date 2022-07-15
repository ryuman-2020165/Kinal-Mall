DROP DATABASE IF EXISTS DBKinalMall2020165;
CREATE	DATABASE DBKinalMall2020165;
USE DBKinalMall2020165;

CREATE TABLE  Cargos (
	codigoCargos int not null auto_increment,
    nombreCargo VARCHAR(45) not null,
    primary key PK_codigoCargos (codigoCargos)
);

CREATE TABLE Departamentos(
	codigoDepartamento int not null auto_increment,
    nombreDepartamento VARCHAR(45) not null,
    primary key PK_codigoDepartamento (codigoDepartamento)
);

CREATE TABLE Horarios(
	codigoHorario int not null auto_increment,
    horarioEntrada VARCHAR(5) not null,
    horarioSalida varchar(5) not null,
    lunes boolean,
    martes boolean,
    miercoles boolean,
    jueves boolean,
	viernes boolean,
    primary key PK_codigoHorario (codigoHorario)
);

CREATE TABLE TipoCliente(
	codigoTipoCliente int not null auto_increment,
    descripcion VARCHAR(45) not null,
    primary key PK_codigoTipoCliente (codigoTipoCliente)
);


CREATE TABLE Administracion(
	codigoAdministracion int not null auto_increment,
    direccion VARCHAR(45) not null,
    telefono VARCHAR(8) not null,
    primary key PK_codigoAdministracion (codigoAdministracion) 
);


CREATE TABLE Locales(
	codigoLocal int not null auto_increment,
    saldoFavor DOUBLE(11,2) default 0.0,
    saldoContra DOUBLE(11,2) default 0.0,
    mesesPendientes int default 0,
    disponibilidad boolean not null,
    valorLocal DOUBLE(11,2) not null,
    valorAdministracion DOUBLE(11,2) not null,
    primary key PK_codigoLocal (codigoLocal)
);

CREATE TABLE Empleados(
	codigoEmpleado int not null auto_increment,
    nombreEmpleado VARCHAR(45) not null,
    apellidoEmpleado VARCHAR(45) not null,
    correoElectronico VARCHAR(45) not null,
    telefonoEmpleado varchar(45) not null,
	fechaContratacion DATE not null,
    sueldo DOUBLE(11,2) not null,
    primary key PK_codigoEmpleado (codigoEmpleado),
    
    codigoDepartamento int not null,
    codigoCargos int not null,
    codigoHorario int not null,
    codigoAdministracion int not null,
    
		constraint FK_Empleados_Departamentos foreign key (codigoDepartamento)
		references Departamentos (codigoDepartamento),
		constraint FK_Empleados_Cargos foreign key (codigoCargos)
        references Cargos (codigoCargos),
        constraint FK_Empleados_Horarios foreign key(codigoHorario)
        references Horarios (codigoHorario),
        constraint FK_Empleados_Administracion foreign key(codigoAdministracion)
        references Administracion (codigoAdministracion)
);


CREATE TABLE Proveedores(
	codigoProveedor int not null auto_increment,
    NITProveedor VARCHAR(45) not null,
    servicioPrestado VARCHAR(45) not null,
    telefonoProveedor VARCHAR(45) not null,
    direccionProveedor VARCHAR(60) not null,
	saldoFavor DOUBLE(11,2) not null,
    saldoContra DOUBLE(11,2) not null,
    primary key PK_codigoProveedor (codigoProveedor),
    
    codigoAdministracion int not null,
    
		constraint FK_Proveedores_Administracion foreign key (codigoAdministracion)
        references Administracion (codigoAdministracion)
);


CREATE TABLE CuentasPorPagar(
	codigoCuentasPorPagar int not null auto_increment,
	numeroFactura VARCHAR(45) not null,
    fechaLimitePago DATE not null,
    estadoPago VARCHAR(45) not null,
    valorNetoPago DOUBLE(11,2) not null,
    primary key PK_codigoCuentasPorPagar (codigoCuentasPorPagar),
    
    codigoAdministracion int not null,
    codigoProveedor int not null,
    
		constraint FK_CuentasPorPagar_Administracion foreign key (codigoAdministracion)
        references Administracion (codigoAdministracion),
        constraint FK_CuentasPorPagar_Proveedores foreign key (codigoProveedor)
        references Proveedores (codigoProveedor)
);

CREATE TABLE Clientes(
	codigoCliente int not null auto_increment,
    nombresCliente VARCHAR(45) not null,
    apellidosCliente VARCHAR(45) not null,
    telefonoCliente VARCHAR(8) not null,
    direccionCliente VARCHAR(60) not null,
    email VARCHAR(45) not null,
	primary key PK_codigoCliente (codigoCliente),
	
    codigoLocal int not null,
    codigoAdministracion int not null,
    codigoTipoCliente int not null,
    
		constraint FK_Clientes_Locales foreign key(codigoLocal)
        references Locales (codigoLocal),
        constraint FK_Clientes_Administracion foreign key(codigoAdministracion)
        references Administracion (codigoAdministracion),
        constraint FK_TipoCliente_TipoCliente foreign key (codigoTipoCliente)
        references TipoCliente (codigoTipoCliente)
);



CREATE TABLE CuentasPorCobrar(
	codigoCuentasPorCobrar int not null auto_increment,
    codigoFactura VARCHAR(45) not null,
    anio YEAR(4) not null,
    mes INT(2)not null,
    valorNetoPago DOUBLE(11,2) not null,
    estadoPago VARCHAR(45) not null,
    primary key PK_codigoCuentasPorCobrar (codigoCuentasPorCobrar),
    
    codigoAdministracion int not null,
    codigoCliente int not null,
    codigoLocal int not null,
    
		constraint FK_CuentasPorCobrar_Administracion foreign key(codigoAdministracion)
        references Administracion (codigoAdministracion),
        constraint FK_CuentasPorCobrar_Clientes foreign key(codigoCliente)
        references Clientes (codigoCliente),
        constraint FK_CuentasPorPagar_Locales foreign key(codigoLocal)
        references Locales (codigoLocal)
);












-- -------------------------------Procedimientos de Administracion------------------------------

-- Agregar
delimiter $$ 
	create procedure sp_AgregarAdministracion(in ag_direccion varchar(45), in ag_telefono varchar(8))
    begin
    insert into Administracion(direccion,telefono)
    values (ag_direccion,ag_telefono);
end $$
delimiter ;

call sp_AgregarAdministracion("Guatemala",98765432);
call sp_AgregarAdministracion("Mixco",12345678);
call sp_AgregarAdministracion("Zacapa",64579484);
select * from Administracion;

-- Editar
delimiter $$
	create procedure sp_EditarAdministracion(in ed_codigoAdministracion int, in ed_direccion varchar(45), in ed_telefono varchar(8))
	begin
	update Administracion
    set
		direccion = ed_direccion,
        telefono = ed_telefono
	where codigoAdministracion = ed_codigoAdministracion;
    end $$
delimiter ;

-- Eliminar
delimiter $$
	create procedure sp_EliminarAdministracion(in el_codigoAdministracion int)
    begin
		delete from Administracion where codigoAdministracion = el_codigoAdministracion;
	end $$
delimiter ;

-- Buscar
delimiter $$
	create procedure sp_BuscarAdministracion(in bu_codigoAdministracion int)
    begin
		select 
			A.codigoAdministracion,
            A.direccion,
            A.telefono
		from Administracion A
			where codigoAdministracion = bu_codigoAdministracion;
	end $$
delimiter ;

-- Listar
delimiter $$
	create procedure sp_ListarAdministracion()
    begin
		select
			A.codigoAdministracion,
            A.direccion,
            A.telefono
		from Administracion A;
	end $$
delimiter ;

-- -------------------------------Procedimientos de Proveedores------------------------------

-- Agregar
delimiter $$
	create procedure sp_AgregarProveedores(in ag_NITProveedor VARCHAR(45),in ag_servicioPrestado VARCHAR(45),
    in ag_telefonoProveedor VARCHAR(45), in ag_direccionProveedor VARCHAR(60),
	in ag_saldoFavor DOUBLE(11,2), in ag_saldoContra DOUBLE(11,2),in ag_codigoAdministracion int)
    begin
		insert into Proveedores(NITProveedor,servicioPrestado, telefonoProveedor,direccionProveedor,saldoFavor,saldoContra,codigoAdministracion)
        values(ag_NITProveedor,ag_servicioPrestado,ag_telefonoProveedor,ag_direccionProveedor,ag_saldoFavor,ag_saldoContra,ag_codigoAdministracion);
	end $$
delimiter ;

call sp_AgregarProveedores('122','Mantenimiento','11111111','Zona 18',144,187,1);
call sp_AgregarProveedores('222','Agua potable','22222222','Zona 3',244,287,1);
call sp_AgregarProveedores('322','Limpieza','33333333','Zona 5',344,387,1);
call sp_AgregarProveedores('422','Mantenimiento','44444444','Zona 17',444,487,1);
call sp_AgregarProveedores('522','Actualizacion','55555555','Zona 19',544,587,1);
call sp_AgregarProveedores('622','Limpieza','66666666','Zona 7',644,687,1);
call sp_AgregarProveedores('722','Maquinaria','77777777','Zona 6',744,787,1);
call sp_AgregarProveedores('822','Computadoras','88888888','Zona 16',844,887,1);
call sp_AgregarProveedores('922','Agua','99999999','Zona 1',944,987,1);
call sp_AgregarProveedores('102','Mantenimiento','11111110','Zona 4',104,107,1);
-- Rene Alejandro Yuman Barco
-- call sp_ListarProveedores();

-- Editar
delimiter $$
	create procedure sp_EditarProveedores(in ed_codigoProveedor int, in ed_NITProveedor VARCHAR(45),in ed_servidoPrestado VARCHAR(45),
    in ed_telefonoProveedor VARCHAR(45), in ed_direccionProveedor VARCHAR(60),
	in ed_saldoFavor DOUBLE(11,2), in ed_saldoContra DOUBLE(11,2)/* in ed_codigoAdministracion int */)
    begin
		update proveedores
        set 
			NITProveedor = ed_NITProveedor,
            servicioPrestado = ed_servidoPrestado,
            telefonoProveedor = ed_telefonoProveedor,
            direccionProveedor = ed_direccionProveedor,
            saldoFavor = ed_saldoFavor,
            saldoContra = ed_saldoContra
            -- codigoAdministracion = ed_codigoAdministracion
		where codigoProveedor = ed_codigoProveedor;
	end $$
delimiter ;



-- Eliminar
delimiter $$
	create procedure sp_EliminarProveedores(in el_codigoProveedor int)
    begin
		delete from Proveedores where codigoProveedor = el_codigoProveedor;
	end $$
delimiter ;

-- Buscar
delimiter $$
	create procedure sp_BuscarProveedores(in bu_codigoProveedor int)
    begin
		select 
			P.codigoProveedor,
			P.NITProveedor,
            P.servicioPrestado,
            P.telefonoProveedor,
            P.direccionProveedor,
            P.saldoFavor,
            P.saldoContra,
            P.codigoAdministracion
		from Proveedores P
			where codigoProveedor = bu_codigoProveedor;
	end$$
delimiter ;

-- Listar
delimiter $$
	create procedure sp_ListarProveedores()
    begin
		select
			P.codigoProveedor,
			P.NITProveedor,
            P.servicioPrestado,
            P.telefonoProveedor,
            
            P.direccionProveedor,
            P.saldoFavor,
            P.saldoContra,
            P.codigoAdministracion
		from Proveedores P;
	end $$
delimiter ;

call sp_ListarProveedores();



delimiter $$
	create procedure sp_SaldoLiquido(in sl_codigoProveedor int)
    begin
		select P.codigoProveedor,
			P.NITProveedor,
            P.servicioPrestado,
            P.telefonoProveedor,
            P.direccionProveedor,
            P.saldoFavor,
            P.saldoContra,
            P.codigoAdministracion,
            p.saldoFavor-p.saldoContra as SaldoLiquido
        from Proveedores p where codigoProveedor=sl_codigoProveedor; -- Rene Alejandro Yuman Barco
    end $$
delimiter ;
-- Rene Alejandro Yuman Barco
call sp_SaldoLiquido(3);



delimiter $$
	create procedure sp_SaldoLiquidoCompleto()
    begin
		select P.codigoProveedor,
			P.NITProveedor,
            P.servicioPrestado,
            P.telefonoProveedor,
            P.direccionProveedor,
            P.saldoFavor,
            P.saldoContra,
            P.codigoAdministracion,
            p.saldoFavor-p.saldoContra as SaldoLiquidoCompleto -- Rene Alejandro Yuman Barco
        from Proveedores p;
    end $$
delimiter ;
call sp_SaldoLiquidoCompleto();
-- -------------------------------Procedimientos de Cuentas Por Pagar------------------------------

-- Agregar
delimiter $$
	create procedure sp_AgregarCuentasPorPagar(in ag_numeroFactura VARCHAR(45), in ag_fechaLimitePago DATE, in ag_estadoPago VARCHAR(45),
    in ag_valorNetoPago DOUBLE(11,2), in ag_codigoAdministracion int, in ag_codigoProveedor int)
    begin
		insert into CuentasPorPagar(numeroFactura,fechaLimitePago,estadoPago,valorNetoPago,codigoAdministracion,codigoProveedor)
        values (ag_numeroFactura,ag_fechaLimitePago,ag_estadoPago,ag_valorNetoPago,ag_codigoAdministracion,ag_codigoProveedor);
    end $$
delimiter ;

call sp_AgregarCuentasPorPagar('123','4-5-12','71',5,1,1);


-- Editar
delimiter $$
	create procedure sp_EditarCuentasPorPagar(in ed_codigoCuentasPorPagar int,in ed_numeroFactura VARCHAR(45), in ed_fechaLimitePago VARCHAR(45), in ed_estadoPago VARCHAR(45),
    in ed_valorNetoPago DOUBLE(11,2)/* in ed_codigoAdministracion int, in ed_codigoProveedor int */) 
    begin
		update CuentasPorPagar
			set
				numeroFactura = ed_numeroFactura,
                fechaLimitePago = ed_fechaLimitePago,
                estadopago = ed_estadoPago,
                valorNetoPago = ed_valorNetoPago
                where codigoCuentasPorPagar = ed_codigoCuentasPorPagar;
	end $$
delimiter ;

-- Eliminar
delimiter $$
	create procedure sp_EliminarCuentasPorPagar (in el_codigoCuentasPorPagar int)
    begin
		delete from CuentasPorPagar where codigoCuentasPorPagar = el_codigoCuentasPorPagar;
	end$$
delimiter ;

-- Buscar
delimiter $$
	create procedure sp_BuscarCuentasPorPagar (in bu_codigoCuentasPorPagar int)
    begin
		select
			C.codigoCuentasPorPagar,
			C.codigoCuentasPorPagar,
            C.numeroFactura,
            C.fechaLimitePago,
            C.estadoPago,
            C.valorNetoPago,
            C.codigoAdministracion,
            C.codigoProveedor
            from CuentasPorPagar C
				where codigoCuentasPorPagar = bu_codigoCuentasPorPagar;
	end $$
delimiter ;

-- Listar
delimiter $$
	create procedure sp_ListarCuentasPorPagar()
    begin
		select
			C.codigoCuentasPorPagar,
            C.numeroFactura,
            C.fechaLimitePago,
            C.estadoPago,
            C.valorNetoPago,
            C.codigoAdministracion,
            C.codigoProveedor
            from CuentasPorPagar C;
	end $$
delimiter ;

call sp_ListarCuentasPorPagar();


-- -------------------------------Locales------------------------------



-- Agregar
Delimiter $$
create procedure sp_AgregarLocales(ag_disponibilidad boolean, ag_valorLocal double(11,2), ag_valorAdministracion double(11,2))
			Begin
            insert into Locales (disponibilidad, valorLocal, valorAdministracion)
            values (ag_disponibilidad, ag_valorLocal, ag_valorAdministracion);
            End $$
Delimiter ;


call sp_AgregarLocales(false,1551,18);
call sp_AgregarLocales(true,2541,252);
call sp_AgregarLocales(false,4854,833);
call sp_AgregarLocales(false,800,454);
call sp_AgregarLocales(true,600,985);
call sp_AgregarLocales(false,760,466);
call sp_AgregarLocales(true,850,677);
call sp_AgregarLocales(false,895,288);
call sp_AgregarLocales(true,95,19);
call sp_AgregarLocales(false,110,10);
-- Rene Alejandro Yuman Barco
-- call sp_ListarLocal();



-- Editar
Delimiter $$
create procedure sp_EditarLocales(ed_codigoLocal int, ed_disponibilidad boolean, ed_valorLocal double(11,2), ed_valorAdministracion double(11,2))
		Begin
        update Locales
			set 
				 
				disponibilidad = ed_disponibilidad, 
				valorLocal = ed_valorLocal, 
				valorAdministracion = ed_valorAdministracion
					where codigoLocal = ed_codigoLocal;
	End$$
Delimiter ;




-- Buscar
Delimiter $$
create procedure sp_BuscarLocales(in bu_codigoLocal int)
		Begin
        select
			l.codigoLocal,
			l.saldoFavor, 
			l.saldoContra, 
			l.mesesPendientes, 
			l.disponibilidad, 
			l.valorLocal, 
			l.valorAdministracion
			from Locales l
				where codigoLocal=bu_codigoLocal;
	End $$
Delimiter ;




-- Listar
Delimiter $$
create procedure sp_ListarLocal()
			Begin
            select 
				l.codigoLocal,
				l.saldoFavor, 
				l.saldoContra, 
				l.mesesPendientes, 
				l.disponibilidad, 
				l.valorLocal, 
				l.valorAdministracion
				from Locales l;
            End $$
Delimiter ;

CALL sp_ListarLocal();



-- Eliminar
Delimiter $$
create procedure sp_EliminarLocal(in el_codigoLocal int )
		Begin
             delete from Locales where codigoLocal=el_codigoLocal;
        End $$
Delimiter ;


-- CalcularLiquido
Delimiter $$
create procedure sp_CalcularLiquido(in cal_codigoLocal int )
	Begin
		select l.saldoFavor-l.saldoContra as resultado 
        from Locales l where codigoLocal=cal_codigoLocal;
    End $$

Delimiter ; 




-- Calcular Meses y Liqquido
Delimiter $$
create procedure sp_CalcularMesesPendientesYLiquido(in cal_codigoLocal int )
	Begin
		select (l.valorLocal*l.mesesPendientes)-(l.saldoFavor-l.saldoContra) as resultado 
        from Locales l where codigoLocal=cal_codigoLocal;
    End $$


Delimiter ; 
-- Rene Alejandro Yuman Barco

call sp_CalcularMesesPendientesYLiquido(1);


-- Calcular Meses y Liquido Completo
Delimiter $$
create procedure sp_CalcularMesesPendientesYLiquidoCompleto(in cal_codigoLocal int )
	Begin
		select (l.valorLocal*l.mesesPendientes)-(l.saldoFavor-l.saldoContra) as resultado 
        from Locales l;
    End $$
-- Rene Alejandro Yuman Barco

Delimiter ; 


call sp_CalcularMesesPendientesYLiquidoCompleto(1);


-- Disponibilad

Delimiter $$
create procedure sp_CalcularDisponibilidad()
	Begin
		select count(disponibilidad) as NoDisponibles from Locales where Disponibilidad = false ;
        select count(disponibilidad) as Disponibles from Locales where Disponibilidad = true ;
        select count(disponibilidad) as Total from Locales;
	End $$
Delimiter ;


CALL sp_CalcularDisponibilidad();

-- -------------------------------Tipo Clientes------------------------------



-- Agregar
Delimiter $$
create procedure sp_AgregarTipoCliente(in ag_descripcion varchar(45))
	Begin
    insert into TipoCliente(descripcion)
    values (ag_descripcion);
    End $$
Delimiter ;  

CALL sp_AgregarTipoCliente('Nuevo');
CALL sp_AgregarTipoCliente('Frecuente');
CALL sp_AgregarTipoCliente('Vip');
CALL sp_AgregarTipoCliente('Normal');
CALL sp_AgregarTipoCliente('Vetado');

-- Editar
Delimiter $$
create procedure sp_EditarTipoCliente(in ed_codigoTipoCliente int, in ed_descripcion varchar(45))
	Begin
    update TipoCliente
		set
			descripcion = ed_descripcion
            where codigoTipoCliente = ed_codigoTipoCliente;
		end $$
Delimiter ;


-- Buscar
Delimiter $$
create procedure sp_BuscarTipoCliente(in bu_codigoTipoCliente int)
	Begin
		select 
			t.codigoTipoCliente,
            t.descripcion
            from TipoCliente t where codigoTipoCliente = bu_codigoTipoCliente;
		end $$
Delimiter;


-- Listar
Delimiter $$
create procedure sp_ListarTipoCliente()
	begin
		select 
			t.codigoTipoCliente,
            t.descripcion
            from TipoCliente t;
		end $$
Delimiter;




-- Eliminar
Delimiter $$
create procedure sp_EliminarTipoCliente(in el_codigoTipoCliente int)
	begin
		delete from TipoCliente where codigoTipoCliente = el_codigoTipoCliente;
	end $$
Delimiter;

CALL sp_ListarTipoCliente();


-- -------------------------------Clientes------------------------------



-- Agregar
Delimiter $$
create procedure sp_AgregarClientes(in ag_nombresCliente varchar(45), ag_apellidosCliente varchar(45), ag_telefonoCliente varchar(8), 
ag_direccionCliente varchar(60) , ag_email varchar(45), ag_codigoLocal int, ag_codigoAdministracion int , ag_codigoTipoCliente int)
			Begin
            insert into Clientes(nombresCliente, apellidosCliente, telefonoCliente, direccionCliente, email, codigoLocal, codigoAdministracion, codigoTipoCliente)
            values ( ag_nombresCliente , ag_apellidosCliente , ag_telefonoCliente , ag_direccionCliente  , ag_email , ag_codigoLocal , ag_codigoAdministracion  , ag_codigoTipoCliente);
            End $$
Delimiter ;


 call sp_AgregarClientes('Alberto','Perez','65943187','3ra calle, 4ta avenida','Alberto777@hotmail.com',1,2,1);

 call sp_AgregarClientes('Carlos','Martinez','64973867','5ta calle, 6ta avenida','Carlos98@hotmail.com',2,1,2);
 
 call sp_AgregarClientes('trevor','juarez','64573154','3ra calle, 4ta avenida','TrevorGamer@hotmail.com',3,2,3);

 call sp_AgregarClientes('Martin','paredes','65498756','1ra calle, 6ta avenida','correo@hotmail.com',4,1,4);
 
 call sp_AgregarClientes('fernanda','asus','32456895','2da calle, 8va avenida','fernanda@hotmail.com',5,2,5);



-- Editar
Delimiter $$
create procedure sp_EditarClientes(in ed_codigoCliente int, ed_nombreCliente varchar(45), ed_apellidoCliente varchar(45), ed_telefonoCliente varchar(8), 
ed_direccionCliente varchar(60) , ed_email varchar(45)/* ed_codigoLocal int, ed_codigoAdministracion int , ed_codigoTipoCliente int*/)
	Begin
    update Clientes 
		set
			nombresCliente = ed_nombreCliente, 
            apellidosCliente = ed_apellidoCliente, 
            telefonoCliente = ed_telefonoCliente,
			direccionCliente = ed_direccionCliente, 
            email = ed_email
           -- codigoLocal = ed_codigoLocal, 
           -- codigoAdministracion = ed_codigoAdministracion,
			-- codigoTipoCliente = ed_codigoTipoCliente 
            where codigoCliente = ed_codigoCliente;
    
    End $$

Delimiter ;









-- Buscar
Delimiter $$
create procedure sp_BuscarClientes(in bu_codigoCliente int)
		Begin
        select 
			c.codigoCliente,
			c.nombresCliente, 
			c.apellidosCliente, 
			c.telefonoCliente, 
			c.direccionCliente,
			c.email, 
            c.codigoLocal, 
            c.codigoAdministracion, 
            c.codigoTipoCliente
                    
		from Clientes c where codigoCliente=bu_codigoCliente;
        End $$
Delimiter ;







-- Listar
Delimiter $$
create procedure sp_ListarClientes()
		Begin
		select 
			c.codigoCliente,
			c.nombresCliente, 
			c.apellidosCliente, 
			c.telefonoCliente, 
			c.direccionCliente,
			c.email, 
            c.codigoLocal, 
            c.codigoAdministracion, 
            c.codigoTipoCliente
		from Clientes c;
        End $$
Delimiter ;


call sp_ListarClientes();






-- Eliminar
Delimiter $$
create procedure sp_EliminarClientes(in el_codigoCliente int)
		Begin
        delete from Clientes where codigoCliente=el_codigoCliente;
        End $$
Delimiter ;

-- -------------------------------Departamentos------------------------------

-- Agregar
Delimiter $$
create procedure sp_AgregarDepartamentos(in ag_nombreDepartamento varchar (45))
	begin
		insert into Departamentos(nombreDepartamento)
        values(ag_nombreDepartamento);
	end $$
Delimiter ;

CALL sp_AgregarDepartamentos('Limpieza');
CALL sp_AgregarDepartamentos('Mantenimiento');
CALL sp_AgregarDepartamentos('Seguridad');


-- Edtiar
Delimiter $$
create procedure sp_EditarDepartamentos(in ed_codigoDepartamento int, in ed_nombreDepartamento varchar(45))
	Begin
		update Departamentos
			set 
				nombreDepartamento = ed_nombreDepartamento
					where codigoDepartamento = ed_codigoDepartamento;
	end $$
Delimiter ;


-- Buscar
Delimiter $$
create procedure sp_BuscarDepartamentos(in bu_codigoDepartamento int)
	Begin
		Select 
			d.codigoDepartamento,
            d.nombreDepartamento
            from Departamentos d where codigoDepartamento = bu_codigoDepartamento;
	end $$
Delimiter ;


-- Listar
Delimiter $$
create procedure sp_ListarDepartamentos()
	Begin
		Select
			d.codigoDepartamento,
            d.nombreDepartamento
            from Departamentos d;
	end $$
Delimiter ;

call sp_ListarDepartamentos();


-- Eliminar
Delimiter $$
create procedure sp_EliminarDepartamentos(in el_codigoDepartamento int)
	Begin
		Delete from Departamentos where codigoDepartamento = el_codigoDepartamento;
	end $$
Delimiter ;



-- -------------------------------Cargos------------------------------


-- Agregar
Delimiter $$
create procedure sp_AgregarCargos(in ag_nombreCargo varchar(45))
	Begin
		insert into Cargos(nombreCargo)
        values(ag_nombreCargo);
	end $$
Delimiter ;

CALL sp_AgregarCargos('Gerente');
CALL sp_AgregarCargos('Encargado');


-- Editar
Delimiter $$
create procedure sp_EditarCargos(in ed_codigoCargos int, in ed_nombreCargo varchar(45))
	Begin
		update Cargos
			set
				nombreCargo = ed_nombreCargo
                where codigoCargos = ed_codigoCargos;
	end $$
Delimiter ;


-- Buscar 
Delimiter $$
create procedure sp_BuscarCargos(in bu_codigoCargos int)
	Begin 
		select
			c.codigoCargos,
            c.nombreCargo
			from Cargos c where codigoCargos = bu_codigoCargos;
	end $$
Delimiter ;
        
        
        
-- Listar
Delimiter $$
create procedure sp_ListarCargos()
	Begin 
		select
			c.codigoCargos,
            c.nombreCargo
			from Cargos c;
	end $$
Delimiter ;

call sp_ListarCargos();

-- Eliminar
Delimiter $$
create procedure sp_ELiminarCargos(in el_codigoCargos int)
	Begin
		Delete from Cargos where codigoCargos = el_codigoCargos;
	end $$
Delimiter ;




-- -------------------------------Horario------------------------------

-- Agregar
Delimiter $$
create procedure sp_AgregarHorarios(in ag_horarioEntrada varchar(5), in ag_horarioSalida varchar(5), in ag_lunes boolean, in ag_martes boolean, in ag_miercoles boolean,
		in ag_jueves boolean, in ag_viernes boolean)
        Begin
			Insert into Horarios(horarioEntrada,horarioSalida, lunes, martes, miercoles, jueves, viernes)
            values (ag_horarioEntrada,ag_horarioSalida, ag_lunes, ag_martes, ag_miercoles, ag_jueves, ag_viernes);
		end $$
Delimiter ;

 call sp_AgregarHorarios('1111','2222',true,false,true,false,true);
call sp_AgregarHorarios('3333','4444',true,false,true,false,true);

-- Editar
Delimiter $$
create procedure sp_EditarHorarios(in ed_codigoHorario int ,in ed_horarioEntrada varchar(5), in ed_horarioSalida varchar(5), in ed_lunes boolean, in ed_martes boolean, in ed_miercoles boolean,
		in ed_jueves boolean, in ed_viernes boolean)
        Begin
			update Horarios
				set 
					horarioEntrada = ed_horarioEntrada,
                    horarioSalida = ed_horarioSalida, 
                    lunes = ed_lunes, 
                    martes = ed_martes, 
                    miercoles = ed_miercoles, 
                    jueves = ed_jueves, 
                    viernes = ed_viernes
                    where codigoHorario = ed_codigoHorario;
		end $$
Delimiter ;


-- Buscar 
Delimiter $$
create procedure sp_BuscarHorarios(in bu_codigoHorario int)
	Begin
		Select
			h.codigoHorario,
			h.horarioEntrada,
            h.horarioSalida, 
            h.lunes, 
            h.martes, 
            h.miercoles, 
            h.jueves, 
            h.viernes
				from Horarios h where codigoHorario = bu_codigoHorario;
	end $$
Delimiter ;


-- Listar
Delimiter $$
create procedure sp_ListarHorarios()
	Begin
		select
			h.codigoHorario,
			h.horarioEntrada,
            h.horarioSalida, 
            h.lunes, 
            h.martes, 
            h.miercoles, 
            h.jueves, 
            h.viernes
				from Horarios h;
	end $$
Delimiter ;

CALL sp_ListarHorarios();

-- Eliminar
Delimiter $$
create procedure sp_EliminarHorarios(in el_codigoHorario int)
	Begin
		Delete from Horarios where codigoHorario = el_codigoHorario;
	end $$
Delimiter ;


-- -------------------------------Empleados------------------------------





-- Agregar
Delimiter $$
	create procedure sp_AgregarEmpleado(in ag_nombreEmpleado varchar(45),in ag_apellidoEmpleado varchar(45),in ag_correoElectronico varchar(45),
			in ag_telefonoEmpleado varchar(8),in ag_fechaContratacion date,in ag_sueldo double,in ag_codigoDepartamento int,in ag_codigoCargo int,in ag_codigoHorario int,in ag_codigoAdministracion int)
		Begin
			Insert into Empleados(nombreEmpleado,apellidoEmpleado,correoElectronico,telefonoEmpleado,fechaContratacion,sueldo,
					codigoDepartamento,codigoCargos,codigoHorario,codigoAdministracion)
						values (ag_nombreEmpleado,ag_apellidoEmpleado,ag_correoElectronico,ag_telefonoEmpleado,ag_fechaContratacion,ag_sueldo,
							ag_codigoDepartamento,ag_codigoCargo,ag_codigoHorario,ag_codigoAdministracion);
		End $$
Delimiter ;

call sp_AgregarEmpleado('Alejandro','Yuman','ryuman-2020165@kinal.edu.gt','12345678','2021-11-15',12356,1,1,1,1);
call sp_AgregarEmpleado('Alison','Yuman','alison20218@gmail.com','1023654','2021-05-09',3500.19,1,1,1,1);
call sp_AgregarEmpleado('Carlos','Yuman','Carlito@gmail.com','15964284','2021-05-09',3500.19,1,1,2,1);






-- Editar
Delimiter $$
create procedure sp_EditarEmpleado(in ed_codigoEmpleado int,in ed_nombreEmpleado varchar(45),in ed_apellidoEmpleado varchar(45),in ed_correoElectronico varchar(45),
			in ed_telefonoEmpleado varchar(8),in ed_fechaContratacion date,in ed_sueldo double)
		Begin
        update Empleados 
			set 
				nombreEmpleado = ed_nombreEmpleado, 
                apellidoEmpleado = ed_apellidoEmpleado, 
                correoElectronico = ed_correoElectronico, 
				telefonoEmpleado = ed_telefonoEmpleado, 
                fechaContratacion = ed_fechaContratacion, 
                sueldo = ed_sueldo
				-- codigoDepartamento = ed_codigoDepartamento, 
                -- codigoCargos = ed_codigoCargo, 
                -- codigoHorario = ed_codigoHorario, 
                -- codigoAdministracion = ed_codigoAdministracion
					where codigoEmpleado = ed_codigoEmpleado;
        End $$
Delimiter ;





-- Buscar
Delimiter $$
create procedure sp_BuscarEmpleado(in bu_codigoEmpleado int)
		Begin
			select 
				e.codigoEmpleado,
				e.nombreEmpleado,
				e.apellidoEmpleado, 
                e.correoElectronico, 
				e.telefonoEmpleado, 
                e.fechaContratacion, 
                e.sueldo, 
                e.codigoDepartamento, 
                e.codigoCargos, 
                e.codigoHorario, 
                e.codigoAdministracion
		from Empleados e where codigoEmpleado=bu_codigoEmpleado;
        End $$

Delimiter ;





-- Listar
Delimiter $$
create procedure sp_ListarEmpleado()
			Begin
            select 
				e.codigoEmpleado,
				e.nombreEmpleado, 
				e.apellidoEmpleado, 
                e.correoElectronico, 
				e.telefonoEmpleado, 
                e.fechaContratacion, 
                e.sueldo, 
                e.codigoDepartamento, 
                e.codigoCargos, 
                e.codigoHorario, 
                e.codigoAdministracion
			from Empleados e;
            End $$
Delimiter ;

call sp_ListarEmpleado();






-- Eliminar
Delimiter $$
create procedure sp_EliminarEmpleado(in el_codigoEmpleado int)
		Begin
        delete from Empleados where codigoEmpleado=el_codigoEmpleado;
        End $$
Delimiter $$





-- -------------------------------Cuentas Por Cobrar------------------------------


-- Agregar
Delimiter $$
create procedure sp_AgregarCuentasPorCobrar(in ag_codigoFactura varchar(45), in ag_anio YEAR(4), in ag_mes int(2), in ag_valorNetoPago double, in ag_estadoPago varchar(45), 
			in ag_codigoAdministracion int, in ag_codigoCliente int, in ag_codigoLocal int)
	Begin
		insert into CuentasPorCobrar(codigoFactura, anio,mes,valorNetoPago,estadoPago,codigoAdministracion,codigoCliente,codigoLocal)
        values (ag_codigoFactura, ag_anio,ag_mes,ag_valorNetoPago,ag_estadoPago,ag_codigoAdministracion,ag_codigoCliente,ag_codigoLocal);
	end$$
Delimiter ;

CALL sp_AgregarCuentasPorCobrar('sasghj','2021', 6,32,'cancelado',1,1,1);
CALL sp_AgregarCuentasPorCobrar('ghj88','2020', 2,57,'no cancelado',1,2,1);
CALL sp_AgregarCuentasPorCobrar('56gh8y','2021', 6,32,'cancelado',1,3,1);
CALL sp_AgregarCuentasPorCobrar('s8hggh','2019', 2,57,'no cancelado',1,4,1);
CALL sp_AgregarCuentasPorCobrar('85jhjk','2021', 2,57,'no cancelado',1,5,1);


-- Editar
Delimiter $$
create procedure sp_EditarCuentasPorCobrar(in ed_codigoCuentasPorCobrar int, in ed_codigoFactura varchar(45), in ed_anio YEAR(4), in ed_mes int(2), in ed_valorNetoPago double, in ed_estadoPago varchar(45)
			)
	Begin
		update cuentasPorCobrar
			set
				codigoFactura = ed_codigoFactura,
                anio = ed_anio,
                mes = ed_mes,
                valorNetoPago = ed_valorNetoPago,
                estadoPago = ed_estadoPago
                
					where codigoCuentasPorCobrar = ed_codigoCuentasPorCobrar;
	end $$
Delimiter ;


-- Buscar 
Delimiter $$
create procedure sp_BuscarCuentasPorCobrar(in bu_codigoCuentasPorCobrar int)
	Begin
		select
			c.codigoCuentasPorCobrar,
			c.codigoFactura,
			c.anio,
			c.mes,
			c.valorNetoPago,
			c.estadoPago,
			c.codigoAdministracion,
			c.codigoCliente,
			c.codigoLocal
			from CuentasPorCobrar c	where codigoCuentasPorCobrar = bu_codigoCuentasPorCobrar;
	end $$
Delimiter ;


-- Listar 
Delimiter $$
create procedure sp_ListarCuentasPorCobrar()
	Begin
		select
			c.codigoCuentasPorCobrar,
			c.codigoFactura,
			c.anio,
			c.mes,
			c.valorNetoPago,
			c.estadoPago,
			c.codigoAdministracion,
			c.codigoCliente,
			c.codigoLocal
			from CuentasPorCobrar c;
	end $$
Delimiter ;

CALL sp_ListarCuentasPorCobrar();

-- Eliminar
Delimiter $$
create procedure sp_EliminarCuentasPorCobrar(in el_codigoCuentasPorCobrar int)
	Begin
		Delete from CuentasPorCobrar where codigoCuentasPorCobrar = el_codigoCuentasPorCobrar;
	end $$
Delimiter ;



CALL sp_ListarTipoCliente();


ALTER USER 'root'@'localhost' identified with mysql_native_password by 'root';



select * from TipoCliente TC inner join Clientes C on
	TC.codigoTipoCliente = C.codigoTipoCliente 
		where TC.descripcion = 'Nuevo';




Create table Usuarios(
	codigoUsuario int not null auto_increment,
	nombreUsuario varchar(100) not null,
    apellidoUsuario varchar(100) not null,
    usuarioLogin varchar(50) not null,
    contrasena varchar(50) not null,
    primary key PK_codigoUsuario (codigoUsuario)


);


Delimiter $$
	Create procedure sp_AgregarUsuario(in nombreUsuario varchar(100),in apellidoUsuario varchar(100),in usuarioLogin varchar(50),in contraseña varchar(50))
		Begin
			insert into usuarios(nombreUsuario, apellidoUsuario, usuarioLogin, contrasena)
            values (nombreUsuario, apellidoUsuario, usuarioLogin, contrasena);
		End $$
Delimiter;



Delimiter $$
	Create procedure sp_ListarUsuarios()
		Begin
			select
				U.codigoUsuario, 
                U.nombreUsuario, 
                U.apellidoUsuario, 
                U.usuarioLogin, 
                U.contrasena
			from Usuarios U;
		End $$

Delimiter ;

call sp_AgregarUsuario('alejandro','Yuman','Ale','yuman');
call sp_ListarUsuarios();


Create table Login(
	usuarioMaster varchar(50) not null,
    passwordLogin varchar(50)not null,
    primary key PK_usuarioMaster(usuarioMaster)
    );



