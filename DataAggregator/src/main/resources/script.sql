-- Table: public.padrone

-- DROP TABLE public.padrone;

create table checkprovince(
	codice int primary key,
	provincia varchar(50),
	sigla varchar(50),
	regione varchar(50)
);

create table esecuzione(
	id_esecuzione serial primary key,
	tempo bigint,
	esito varchar(50)
);

create table processo(
	uuid_processo serial primary key,
	data_ora timestamp,
	status varchar(50)
);