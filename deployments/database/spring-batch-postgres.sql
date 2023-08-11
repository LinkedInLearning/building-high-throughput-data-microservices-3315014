- public.app_registration definition

-- Drop table

-- DROP TABLE app_registration;

CREATE TABLE app_registration (
	id int8 NOT NULL,
	object_version int8 NULL,
	default_version bool NULL,
	metadata_uri text NULL,
	"name" varchar(255) NULL,
	"type" int4 NULL,
	uri text NULL,
	"version" varchar(255) NULL,
	CONSTRAINT app_registration_pkey PRIMARY KEY (id)
);


-- public.audit_records definition

-- Drop table

-- DROP TABLE audit_records;

CREATE TABLE audit_records (
	id int8 NOT NULL,
	audit_action int8 NULL,
	audit_data text NULL,
	audit_operation int8 NULL,
	correlation_id varchar(255) NULL,
	created_by varchar(255) NULL,
	created_on timestamp NULL,
	platform_name varchar(255) NULL,
	CONSTRAINT audit_records_pkey PRIMARY KEY (id)
);


-- public.batch_job_instance definition

-- Drop table

-- DROP TABLE batch_job_instance;

CREATE TABLE batch_job_instance (
	job_instance_id int8 NOT NULL,
	"version" int8 NULL,
	job_name varchar(100) NOT NULL,
	job_key varchar(32) NOT NULL,
	CONSTRAINT batch_job_instance_pkey PRIMARY KEY (job_instance_id),
	CONSTRAINT job_inst_un UNIQUE (job_name, job_key)
);



-- public.stream_definitions definition

-- Drop table

-- DROP TABLE stream_definitions;

CREATE TABLE stream_definitions (
	definition_name varchar(255) NOT NULL,
	definition text NULL,
	description varchar(255) NULL,
	original_definition text NULL,
	CONSTRAINT stream_definitions_pkey PRIMARY KEY (definition_name)
);


-- public.task_definitions definition

-- Drop table

-- DROP TABLE task_definitions;

CREATE TABLE task_definitions (
	definition_name varchar(255) NOT NULL,
	definition text NULL,
	description varchar(255) NULL,
	CONSTRAINT task_definitions_pkey PRIMARY KEY (definition_name)
);


-- public.task_deployment definition

-- Drop table

-- DROP TABLE task_deployment;

CREATE TABLE task_deployment (
	id int8 NOT NULL,
	object_version int8 NULL,
	task_deployment_id varchar(255) NOT NULL,
	task_definition_name varchar(255) NOT NULL,
	platform_name varchar(255) NOT NULL,
	created_on timestamp NULL,
	CONSTRAINT task_deployment_pkey PRIMARY KEY (id)
);


-- public.task_execution definition

-- Drop table

-- DROP TABLE task_execution;

CREATE TABLE task_execution (
	task_execution_id int8 NOT NULL,
	start_time timestamp NULL,
	end_time timestamp NULL,
	task_name varchar(100) NULL,
	exit_code int4 NULL,
	exit_message varchar(2500) NULL,
	error_message varchar(2500) NULL,
	last_updated timestamp NULL,
	external_execution_id varchar(255) NULL,
	parent_execution_id int8 NULL,
	CONSTRAINT task_execution_pkey PRIMARY KEY (task_execution_id)
);


-- public.task_lock definition

-- Drop table

-- DROP TABLE task_lock;

CREATE TABLE task_lock (
	lock_key bpchar(36) NOT NULL,
	region varchar(100) NOT NULL,
	client_id bpchar(36) NULL,
	created_date timestamp NOT NULL,
	CONSTRAINT lock_pk PRIMARY KEY (lock_key, region)
);


-- public.batch_job_execution definition

-- Drop table

-- DROP TABLE batch_job_execution;

CREATE TABLE batch_job_execution (
	job_execution_id int8 NOT NULL,
	"version" int8 NULL,
	job_instance_id int8 NOT NULL,
	create_time timestamp NOT NULL,
	start_time timestamp NULL,
	end_time timestamp NULL,
	status varchar(10) NULL,
	exit_code varchar(2500) NULL,
	exit_message varchar(2500) NULL,
	last_updated timestamp NULL,
	job_configuration_location varchar(2500) NULL,
	CONSTRAINT batch_job_execution_pkey PRIMARY KEY (job_execution_id),
	CONSTRAINT job_inst_exec_fk FOREIGN KEY (job_instance_id) REFERENCES batch_job_instance(job_instance_id)
);


-- public.batch_job_execution_context definition

-- Drop table

-- DROP TABLE batch_job_execution_context;

CREATE TABLE batch_job_execution_context (
	job_execution_id int8 NOT NULL,
	short_context varchar(2500) NOT NULL,
	serialized_context text NULL,
	CONSTRAINT batch_job_execution_context_pkey PRIMARY KEY (job_execution_id),
	CONSTRAINT job_exec_ctx_fk FOREIGN KEY (job_execution_id) REFERENCES batch_job_execution(job_execution_id)
);


-- public.batch_job_execution_params definition

-- Drop table

-- DROP TABLE batch_job_execution_params;

CREATE TABLE batch_job_execution_params (
	job_execution_id int8 NOT NULL,
	type_cd varchar(6) NOT NULL,
	key_name varchar(100) NOT NULL,
	string_val varchar(250) NULL,
	date_val timestamp NULL,
	long_val int8 NULL,
	double_val float8 NULL,
	identifying bpchar(1) NOT NULL,
	CONSTRAINT job_exec_params_fk FOREIGN KEY (job_execution_id) REFERENCES batch_job_execution(job_execution_id)
);


-- public.batch_step_execution definition

-- Drop table

-- DROP TABLE batch_step_execution;

CREATE TABLE batch_step_execution (
	step_execution_id int8 NOT NULL,
	"version" int8 NOT NULL,
	step_name varchar(100) NOT NULL,
	job_execution_id int8 NOT NULL,
	start_time timestamp NOT NULL,
	end_time timestamp NULL,
	status varchar(10) NULL,
	commit_count int8 NULL,
	read_count int8 NULL,
	filter_count int8 NULL,
	write_count int8 NULL,
	read_skip_count int8 NULL,
	write_skip_count int8 NULL,
	process_skip_count int8 NULL,
	rollback_count int8 NULL,
	exit_code varchar(2500) NULL,
	exit_message varchar(2500) NULL,
	last_updated timestamp NULL,
	CONSTRAINT batch_step_execution_pkey PRIMARY KEY (step_execution_id),
	CONSTRAINT job_exec_step_fk FOREIGN KEY (job_execution_id) REFERENCES batch_job_execution(job_execution_id)
);
CREATE INDEX step_name_idx ON public.batch_step_execution USING btree (step_name);


-- public.batch_step_execution_context definition

-- Drop table

-- DROP TABLE batch_step_execution_context;

CREATE TABLE batch_step_execution_context (
	step_execution_id int8 NOT NULL,
	short_context varchar(2500) NOT NULL,
	serialized_context text NULL,
	CONSTRAINT batch_step_execution_context_pkey PRIMARY KEY (step_execution_id),
	CONSTRAINT step_exec_ctx_fk FOREIGN KEY (step_execution_id) REFERENCES batch_step_execution(step_execution_id)
);


-- public.task_execution_metadata definition

-- Drop table

-- DROP TABLE task_execution_metadata;

CREATE TABLE task_execution_metadata (
	id int8 NOT NULL,
	task_execution_id int8 NOT NULL,
	task_execution_manifest text NULL,
	CONSTRAINT task_execution_metadata_pkey PRIMARY KEY (id),
	CONSTRAINT task_metadata_fk FOREIGN KEY (task_execution_id) REFERENCES task_execution(task_execution_id)
);


-- public.task_execution_params definition

-- Drop table

-- DROP TABLE task_execution_params;

CREATE TABLE task_execution_params (
	task_execution_id int8 NOT NULL,
	task_param varchar(2500) NULL,
	CONSTRAINT task_exec_params_fk FOREIGN KEY (task_execution_id) REFERENCES task_execution(task_execution_id)
);
CREATE INDEX task_execution_id_idx ON public.task_execution_params USING btree (task_execution_id);


-- public.task_task_batch definition

-- Drop table

-- DROP TABLE task_task_batch;

CREATE TABLE task_task_batch (
	task_execution_id int8 NOT NULL,
	job_execution_id int8 NOT NULL,
	CONSTRAINT task_exec_batch_fk FOREIGN KEY (task_execution_id) REFERENCES task_execution(task_execution_id)


-- public.batch_job_execution_seq definition

-- DROP SEQUENCE public.batch_job_execution_seq;

CREATE SEQUENCE public.batch_job_execution_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;


-- public.batch_job_seq definition

-- DROP SEQUENCE public.batch_job_seq;

CREATE SEQUENCE public.batch_job_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;


-- public.batch_step_execution_seq definition

-- DROP SEQUENCE public.batch_step_execution_seq;

CREATE SEQUENCE public.batch_step_execution_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;


-- public.hibernate_sequence definition

-- DROP SEQUENCE public.hibernate_sequence;

CREATE SEQUENCE public.hibernate_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;


-- public.task_execution_metadata_seq definition

-- DROP SEQUENCE public.task_execution_metadata_seq;

CREATE SEQUENCE public.task_execution_metadata_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;


-- public.task_seq definition

-- DROP SEQUENCE public.task_seq;

CREATE SEQUENCE public.task_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;