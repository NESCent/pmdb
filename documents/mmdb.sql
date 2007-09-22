drop table  mm_experiment_study_attr_cvterm_assoc;
drop table mm_data_record;
drop table mm_experiment_value;
drop table mm_experiment_study;
drop table mm_pop_sample_attr_cvterm_assoc;
drop table mm_species_attr_cvterm_assoc;
drop table mm_population_sample;
drop table mm_mating_system_study;
drop table mm_reference_part;
drop table mm_species;
drop table mm_reference;
drop table mm_term_relationship;
drop table mm_user_acct_role_assoc;
drop table mm_user_account;
drop table mm_creation_history;
drop table mm_permission;
drop table mm_role;
drop table mm_cv_term;




drop sequence mm_data_record_seq;
drop sequence mm_pop_sample_attr_cvterm_assoc_seq;
drop sequence mm_species_attr_cvterm_assoc_seq;
drop sequence mm_experiment_value_seq;
drop sequence mm_experiment_study_seq;
drop sequence mm_population_sample_seq;
drop sequence mm_mating_system_study_seq;
drop sequence mm_reference_part_seq;
drop sequence mm_species_seq;
drop sequence mm_reference_seq;
drop sequence mm_term_relationship_seq;
drop sequence mm_user_acct_role_assoc_seq;
drop sequence mm_user_account_seq;
drop sequence mm_creation_history_seq;
drop sequence mm_permission_seq;
drop sequence mm_role_seq;
drop sequence mm_cv_term_seq;
drop sequence mm_experiment_study_attr_cvterm_assoc_seq;


create sequence mm_data_record_seq START WITH 1;
create sequence mm_pop_sample_attr_cvterm_assoc_seq START WITH 1;
create sequence mm_species_attr_cvterm_assoc_seq START WITH 1;
create sequence mm_experiment_value_seq START WITH 1;
create sequence mm_experiment_study_seq START WITH 1;
create sequence mm_population_sample_seq start 1;
create sequence mm_mating_system_study_seq START WITH 1;
create sequence mm_reference_part_seq START WITH 1;
create sequence mm_species_seq START WITH 1;
create sequence mm_reference_seq START WITH 1;
create sequence mm_term_relationship_seq START WITH 1;
create sequence mm_cv_term_seq START WITH 1;
create sequence mm_user_acct_role_assoc_seq START WITH 1;
create sequence mm_user_account_seq START WITH 1;
create sequence mm_creation_history_seq START WITH 1;
create sequence mm_permission_seq START WITH 1;
create sequence mm_role_seq START WITH 1;
create sequence mm_experiment_study_attr_cvterm_assoc_seq start with 1;



CREATE TABLE mm_cv_term
   (	cvterm_oid integer DEFAULT nextval('mm_cv_term_seq') CONSTRAINT pk_cvterm_oid  PRIMARY KEY,
	namespace VARCHAR(64) NOT NULL,
	name VARCHAR(128)  NOT NULL,
	description VARCHAR(128),
	synonym_name VARCHAR(32),
	value_type VARCHAR(15),
	is_value_computed VARCHAR(3));

CREATE TABLE mm_term_relationship
   (	term_relationship_oid integer DEFAULT nextval('mm_term_relationship_seq') CONSTRAINT pk_term_relationship_oid   PRIMARY KEY,
	cvterm_subject_oid integer,
	cvterm_predicate_oid integer,
	cvterm_object_oid integer,
	 CONSTRAINT  fk_term_relationship_cvterm_subject_oid FOREIGN KEY (cvterm_subject_oid)
                         REFERENCES mm_cv_term(cvterm_oid),
	 CONSTRAINT  fk_term_relationship_cvterm_predicate_oid FOREIGN KEY (cvterm_predicate_oid)
                         REFERENCES mm_cv_term(cvterm_oid),
	 CONSTRAINT  fk_term_relationship_cvterm_object_oid FOREIGN KEY (cvterm_object_oid)
                         REFERENCES mm_cv_term(cvterm_oid));  


CREATE TABLE mm_reference
   (	reference_oid integer DEFAULT nextval('mm_reference_seq') CONSTRAINT pk_reference_oid    PRIMARY KEY,
	citation text,
	full_reference text);



CREATE TABLE mm_reference_part
   (	reference_part_oid integer DEFAULT nextval('mm_reference_part_seq') CONSTRAINT pk_reference_part_oid     PRIMARY KEY,
	name VARCHAR(64) NOT NULL,
	reference_oid integer,
	     CONSTRAINT  fk_reference_part_reference_oid FOREIGN KEY (reference_oid)
                         REFERENCES mm_reference(reference_oid));

CREATE TABLE mm_species
   (	species_oid integer DEFAULT nextval('mm_species_seq') CONSTRAINT pk_species_oid     PRIMARY KEY,
	family VARCHAR(64),
	genus VARCHAR(64) NOT NULL,
	species VARCHAR(64) NOT NULL,
	CONSTRAINT uk_species_family UNIQUE (family,genus,species) 
);

CREATE TABLE mm_mating_system_study
   (	mating_system_study_oid integer DEFAULT nextval('mm_mating_system_study_seq') CONSTRAINT pk_mating_system_study_oid      PRIMARY KEY,
	species_oid integer,
	latitude VARCHAR(256), 
	reference_part_oid integer,
	CONSTRAINT  fk_mating_system_study_species_oid FOREIGN KEY (species_oid)
                         REFERENCES mm_species(species_oid),
	CONSTRAINT  fk_mating_system_study_reference_part_oid FOREIGN KEY (reference_part_oid)
                         REFERENCES mm_reference_part(reference_part_oid));


CREATE TABLE mm_population_sample
   (	population_sample_oid integer DEFAULT nextval('mm_population_sample_seq') CONSTRAINT pk_population_sample_oid      PRIMARY KEY,
	geographic_location text,  
	population text,
	year VARCHAR(10),
	comments text,
	species_oid integer,
	     CONSTRAINT  fk_population_sample_species_oid FOREIGN KEY (species_oid)
                         REFERENCES mm_species(species_oid));
      


	


CREATE TABLE mm_experiment_study
   (	experiment_study_oid integer DEFAULT nextval('mm_experiment_study_seq') CONSTRAINT pk_experiment_study_oid      PRIMARY KEY,
	name VARCHAR(64),
	reference_part_oid integer, 
	population_sample_oid integer,
	type_cvterm_oid integer, 
		
	
	     CONSTRAINT  fk_experiment_study_reference_part_oid FOREIGN KEY (reference_part_oid)
                         REFERENCES mm_reference_part(reference_part_oid),

	     CONSTRAINT  fk_type_cvterm_oid FOREIGN KEY (type_cvterm_oid)
                         REFERENCES mm_cv_term(cvterm_oid),

	     CONSTRAINT  fk_experiment_study_pop_sample_oid FOREIGN KEY (population_sample_oid)
                         REFERENCES mm_population_sample(population_sample_oid));


CREATE TABLE mm_experiment_study_attr_cvterm_assoc
   (	mmexp_oid integer DEFAULT nextval('mm_experiment_study_attr_cvterm_assoc_seq') CONSTRAINT mmexp_oid  PRIMARY KEY,
	value text NOT NULL,
	experiment_study_oid integer,
	cvterm_oid integer,
	CONSTRAINT  fk_exp_study_attr_assoc_experiment_study_oid FOREIGN KEY (experiment_study_oid)
                         REFERENCES mm_experiment_study(experiment_study_oid),
	CONSTRAINT  fk_exp_study_attr_assoc_cvterm_oid FOREIGN KEY (cvterm_oid)
                         REFERENCES mm_cv_term(cvterm_oid));


       
CREATE TABLE mm_experiment_value
   (	experiment_value_oid integer DEFAULT nextval('mm_experiment_value_seq') CONSTRAINT pk_experiment_value_oid    PRIMARY KEY,
	experiment_study_oid integer,
	value VARCHAR(256),
	cvterm_oid integer,
	
	     CONSTRAINT  fk_experiment_value_study_oid FOREIGN KEY (experiment_study_oid)
                         REFERENCES mm_experiment_study(experiment_study_oid),
	     CONSTRAINT  fk_experiment_value_cvterm_oid FOREIGN KEY (cvterm_oid)
                         REFERENCES mm_cv_term(cvterm_oid));


CREATE TABLE mm_data_record
   (	data_record_oid integer DEFAULT nextval('mm_data_record_seq') CONSTRAINT pk_data_record_oid     PRIMARY KEY,
	name VARCHAR(64) ,
	type VARCHAR(64) ,
	out_crossing_value decimal ,
	selfing_value decimal ,
	out_crossing_std_dev decimal ,
	selfing_std_dev decimal , 
	experiment_study_oid integer,
	development_stage_cvterm_oid integer,

	CONSTRAINT  fk_data_record_experiment_study_oid FOREIGN KEY (experiment_study_oid)
                         REFERENCES mm_experiment_study(experiment_study_oid),
	CONSTRAINT  fk_data_record_stage_cvterm_oid FOREIGN KEY (development_stage_cvterm_oid )
                         REFERENCES mm_cv_term(cvterm_oid));


       
CREATE TABLE mm_pop_sample_attr_cvterm_assoc
   (	mpsaca_oid integer DEFAULT nextval('mm_pop_sample_attr_cvterm_assoc_seq') CONSTRAINT pk_mpsaca_oid     PRIMARY KEY,
	value text NOT NULL,
	population_sample_oid integer,
	cvterm_oid integer,
	CONSTRAINT  fk_pop_sample_attr_cvterm_assoc_population_sample_oid FOREIGN KEY (population_sample_oid)
                         REFERENCES mm_population_sample(population_sample_oid),
	CONSTRAINT  fk_pop_sample_attr_cvterm_assoc_cvterm_oid FOREIGN KEY (cvterm_oid)
                         REFERENCES mm_cv_term(cvterm_oid));

CREATE TABLE mm_species_attr_cvterm_assoc
   (	msaca_oid integer DEFAULT nextval('mm_species_attr_cvterm_assoc_seq') CONSTRAINT pk_msaca_oid    PRIMARY KEY,
	value text NOT NULL,
	mating_system_study_oid integer,
	cvterm_oid integer,
	CONSTRAINT  fk_species_attr_cvterm_assoc_mating_system_study_oid FOREIGN KEY (mating_system_study_oid)
                         REFERENCES mm_mating_system_study(mating_system_study_oid),
	CONSTRAINT  fk_species_attr_cvterm_assoc_cvterm_oid FOREIGN KEY (cvterm_oid)
                         REFERENCES mm_cv_term(cvterm_oid));



CREATE TABLE mm_user_account
(	
	user_account_oid  integer DEFAULT nextval('mm_user_account_seq') CONSTRAINT pk_user_account_oid  PRIMARY KEY,
	user_name VARCHAR(64) NOT NULL,
	password VARCHAR(64)  NOT NULL,
	create_date date,
	enable_disable_status VARCHAR(1) NOT NULL,
		CONSTRAINT uk_user_account_password  UNIQUE (user_name,password ));


CREATE TABLE mm_role
(	role_oid integer DEFAULT nextval('mm_role_seq') CONSTRAINT pk_role_oid PRIMARY KEY,
	role_name VARCHAR(64) NOT NULL);


 
CREATE TABLE mm_user_acct_role_assoc
(	uara_oid integer DEFAULT nextval('mm_user_acct_role_assoc_seq') CONSTRAINT pk_user_acct_role_assoc_uara_oid PRIMARY KEY,
	role_oid  integer NOT NULL,
	user_account_oid integer NOT NULL,
	        CONSTRAINT  fk_user_acct_role_assoc_role_oid FOREIGN KEY (role_oid)
                         REFERENCES mm_role (role_oid),
	        CONSTRAINT  fk_user_acct_role_assoc_user_account_oid FOREIGN KEY (user_account_oid)
                         REFERENCES mm_user_account (user_account_oid));


CREATE TABLE mm_permission
(	permission_oid integer DEFAULT nextval('mm_permission_seq') CONSTRAINT pk_permission_oid PRIMARY KEY,
	access VARCHAR(16) NOT NULL,
	resource_cvterm_oid integer NOT NULL,
	scope VARCHAR(8) NOT NULL,
	role_oid integer,
	        CONSTRAINT  fk_permission_resource_cvterm_oid FOREIGN KEY (resource_cvterm_oid)
                         REFERENCES mm_cv_term (cvterm_oid),
	        CONSTRAINT  fk_permission_role_oid FOREIGN KEY (role_oid)
                         REFERENCES mm_role (role_oid));



CREATE TABLE mm_creation_history
   (	creation_history_oid integer DEFAULT 
nextval('mm_creation_history_seq') CONSTRAINT pk_creation_history_oid PRIMARY KEY,
	object_oid integer NOT NULL,
	username VARCHAR(64) NOT NULL,
	object_name VARCHAR(32) NOT NULL);

insert into mm_cv_term (namespace,name)  values ('resource_access','MixedMating');



insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fci-Ft/F','Fruits/Flower','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fio-Ft/F','Fruits/Flower','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fis-Ft/F','Fruits/Flower','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fin-Ft/F','Fruits/Flower','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fen-Ft/F','Fruits/Flower','float',null,null);


insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fci-S/Ft','Seeds/Fruit','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fio-S/Ft','Seeds/Fruit','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fis-S/Ft','Seeds/Fruit','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fin-S/Ft','Seeds/Fruit','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fen-S/Ft','Seeds/Fruit','float',null,null);




insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fci-S/O','Seeds/Ovule','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fio-S/O','Seeds/Ovule','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fis-S/O','Seeds/Ovule','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fin-S/O','Seeds/Ovule','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fen-S/O','Seeds/Ovule','float',null,null);


insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fci-S/F','Seeds/Flower','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fio-S/F','Seeds/Flower','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fis-S/F','Seeds/Flower','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fin-S/F','Seeds/Flower','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fen-S/F','Seeds/Flower','float',null,null);

insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fci-S/tO','Seeds/Total Ovule','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fio-S/tO','Seeds/Total Ovule','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fis-S/tO','Seeds/Total Ovule','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fin-S/tO','Seeds/Total Ovule','float',null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','Fen-S/tO','Seeds/Total Ovule','float',null,null);

insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','(Fci/Fio)','Autofertility','float',null,null);

insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('pollination_attribute','([Fin-Fen]/Fin)','Repro Assurance','float',null,null);


insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('exp_pop_attribute','nPl','Number of Plants',null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('exp_pop_attribute','nFls','Number of Flowers',null,null,null);







insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','taxcat',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','latitude_quantitative',null,null,null,null);

insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','biome',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','growth_form',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','l_form',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','sexual_system',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','life_history',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','si_mechanism',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','flower_size','flower size',null,null,null);

insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','flowers_per_inflorescence','flowers per inflorescence',null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','number_of_inflorescences','number of inflorescences',null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','total_number_of_flowers','total number of flowers',null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','number_of_open_flowers_per_inflorescence','number of open flowers per inflorescence',null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','number_of_inflorescences_with_open flowers','number of inflorescences with open flowers',null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','total_number_of_open_flowers','total number of open flowers',null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','flower_size_varies','flower size varies',null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','inflorescence_varies','inflorescence varies',null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','inflorescence_size','inflorescence size',null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','open_inflorescences','open inflorescences',null,null,null);


insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','genotype',null,null,null,null);


insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','dichogamy',null,null,null,null);

insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','herkogamy',null,null,null,null);

insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','floral_symmetry',null,null,null,null);


insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','reproduction',null,null,null,null);

insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','pollinator',null,null,null,null);


insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','reference',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','supplementary_reference',null,null,null,null);

insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','herkogamy_varies',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','dichogamy_varies',null,null,null,null);


insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','bertin',null,null,null,null);

insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','nceas',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','Nectar',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('species_attribute','Mean-tm',null,null,null,null);


insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('inbreed_exp_attribute','Design',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('inbreed_exp_attribute','parents',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('inbreed_exp_attribute','families',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('inbreed_exp_attribute','pollen',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('inbreed_exp_attribute','numberOfFamilies',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('inbreed_exp_attribute','Experimental Detail',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('inbreed_exp_attribute','environment',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('inbreed_exp_attribute','sample',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('inbreed_exp_attribute','type',null,null,null,null);

insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('stage_attribute','EARLY: stage 1',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('stage_attribute','GERM: stage 2',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('stage_attribute','JUVENILE: stage 3',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('stage_attribute','ADULT: stage 4',null,null,null,null);

insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('study type ontology','inbreeding depression study',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('study type ontology','experimental pollination study',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('study type ontology','outcrossing database study',null,null,null,null);
insert into mm_cv_term(namespace,name,description,value_type,synonym_name,is_value_computed) values ('study type ontology','species attributes study',null,null,null,null);











