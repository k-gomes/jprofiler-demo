DROP TABLE IF EXISTS bibliotheque;

CREATE TABLE bibliotheque(
                             isbn VARCHAR(25) NOT NULL,
                             author VARCHAR(50) NOT NULL,
                             title VARCHAR(50) NOT NULL,
                             publicationyear int4 NOT NULL,
                             stock int4 DEFAULT NULL,
                             estimationtotal decimal(4,0) DEFAULT NULL,
                             rare int4 DEFAULT 0,
                             statut VARCHAR(25) DEFAULT 'A METTRE A JOUR',
                             CONSTRAINT pk_biblio PRIMARY KEY (isbn)
);

-- GENERATE 20 000 DATA INTO public.bibliotheque;
INSERT INTO bibliotheque
SELECT
    random_numeric_string(25) as isbn,
    random_string(50) as title,
    random_string(50) as author,
    random_int(10111) as stock,
    random_int(1048484) as estimationtotal,
    random_int(2024) as publicationyear
FROM generate_series(1,20000);

/*CREATE INDEX IF NOT EXISTS bibliotheque_title_idx ON bibliotheque USING BTREE (title);*/
/*CREATE INDEX IF NOT EXISTS bibliotheque_substring_title_idx ON bibliotheque USING BTREE (SUBSTR(title,1,1));*/

DROP TABLE IF EXISTS author_fr;

CREATE TABLE author_fr(
                             id varchar(255) NOT NULL,
                             name VARCHAR(50) NOT NULL,
                             surname VARCHAR(50) NOT NULL,
                             birthyear int4 NOT NULL,
                             CONSTRAINT pk_author PRIMARY KEY (id)
);

-- GENERATE 20 000 DATA INTO public.author_fr;
INSERT INTO author_fr
SELECT
    random_numeric_string(250) as id,
    random_string(50) as name,
    random_string(50) as surname,
    random_int(2024) as birthyear
FROM generate_series(1,20000);