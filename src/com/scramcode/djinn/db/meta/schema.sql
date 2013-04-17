CREATE TABLE PROJECTS ( 
	project_key			INTEGER 		IDENTITY,
	project_name		VARCHAR 		NOT NULL
);

CREATE TABLE LOCATIONS ( 
	location_key		INTEGER 		IDENTITY ,
	absolute_path 		VARCHAR			NOT NULL ,
	type 				INTEGER 		NOT NULL ,
	project_key			INTEGER 		NOT NULL ,
	CONSTRAINT project_key_fk FOREIGN KEY ( project_key ) 
    REFERENCES PROJECTS ( project_key ) ON DELETE CASCADE
);

CREATE  TABLE PACKAGES ( 
	package_key 		INTEGER 		IDENTITY,
	qname 				VARCHAR(1024) 	NOT NULL,
	location_key        INTEGER 		NOT NULL,	
	CONSTRAINT location_key_fk FOREIGN KEY ( location_key ) 
    REFERENCES LOCATIONS ( location_key ) ON DELETE CASCADE
);

CREATE  TABLE CLASSES ( 
	class_key			INTEGER 		IDENTITY,	
	name 				VARCHAR(255) 	NOT NULL,	
	cname 				VARCHAR(255) 	NOT NULL,
	access				INTEGER			NOT NULL,
	package_key 		INTEGER 		NOT NULL,	
	location_key 		INTEGER 		NOT NULL,		
    CONSTRAINT package_key_fk FOREIGN KEY ( package_key ) REFERENCES PACKAGES ( package_key ) ON DELETE CASCADE,
    CONSTRAINT location_key_fk2 FOREIGN KEY ( location_key ) REFERENCES LOCATIONS ( location_key ) ON DELETE CASCADE    
);

CREATE  TABLE METHODS ( 
	method_key 			INTEGER 		IDENTITY ,
	name				VARCHAR(255)	NOT NULL ,	
	access				INTEGER			NOT NULL ,
	class_key 			INTEGER 		NOT NULL ,	
    CONSTRAINT methods_class_key_fk FOREIGN KEY ( class_key ) REFERENCES CLASSES ( class_key ) ON DELETE CASCADE
);

CREATE  TABLE FIELDS ( 
	field_key 			INTEGER			IDENTITY,
	name				VARCHAR(255) 	NOT NULL,	
	access				INTEGER			NOT NULL,
	class_key 			INTEGER 		NOT NULL,	
    CONSTRAINT fields_class_key_fk FOREIGN KEY ( class_key ) REFERENCES CLASSES ( class_key ) ON DELETE CASCADE	
);

CREATE  TABLE CLASS_REFERENCES ( 
	class_reference_key	INTEGER			IDENTITY,
	class_key			INTEGER			NOT NULL,
	cname				VARCHAR(1024)	NOT NULL,
    CONSTRAINT class_references_class_key_fk FOREIGN KEY ( class_key ) REFERENCES CLASSES ( class_key ) ON DELETE CASCADE		
);


CREATE INDEX locations_fk_index ON LOCATIONS (project_key);
CREATE INDEX packages_fk_index ON PACKAGES(location_key);
CREATE INDEX classes_fk_index ON CLASSES(package_key);
CREATE INDEX methods_fk_index ON METHODS(class_key);
CREATE INDEX fields_fk_index ON FIELDS(class_key);
CREATE INDEX class_references_fk_index ON CLASS_REFERENCES(class_key);
CREATE INDEX class_references_cname_index ON CLASS_REFERENCES(cname);
