--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.25
-- Dumped by pg_dump version 9.3.25
-- Started on 2023-09-19 15:22:13

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 1 (class 3079 OID 11750)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2010 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 172 (class 1259 OID 23234)
-- Name: animals; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.animals (
    id bigint NOT NULL,
    name character varying(255),
    species_id bigint,
    zone_id bigint
);


ALTER TABLE public.animals OWNER TO postgres;

--
-- TOC entry 171 (class 1259 OID 23232)
-- Name: animals_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.animals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.animals_id_seq OWNER TO postgres;

--
-- TOC entry 2011 (class 0 OID 0)
-- Dependencies: 171
-- Name: animals_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.animals_id_seq OWNED BY public.animals.id;


--
-- TOC entry 174 (class 1259 OID 23242)
-- Name: comments; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.comments (
    id bigint NOT NULL,
    animal_id bigint,
    author character varying(255),
    body character varying(255),
    date timestamp(6) without time zone
);


ALTER TABLE public.comments OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 23240)
-- Name: comments_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.comments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.comments_id_seq OWNER TO postgres;

--
-- TOC entry 2012 (class 0 OID 0)
-- Dependencies: 173
-- Name: comments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.comments_id_seq OWNED BY public.comments.id;


--
-- TOC entry 176 (class 1259 OID 23253)
-- Name: replies; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.replies (
    id bigint NOT NULL,
    author character varying(255),
    body character varying(255),
    comment_id bigint,
    date timestamp(6) without time zone
);


ALTER TABLE public.replies OWNER TO postgres;

--
-- TOC entry 175 (class 1259 OID 23251)
-- Name: replies_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.replies_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.replies_id_seq OWNER TO postgres;

--
-- TOC entry 2013 (class 0 OID 0)
-- Dependencies: 175
-- Name: replies_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.replies_id_seq OWNED BY public.replies.id;


--
-- TOC entry 178 (class 1259 OID 23264)
-- Name: species; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.species (
    id bigint NOT NULL,
    name character varying(255),
    zone_id bigint
);


ALTER TABLE public.species OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 23262)
-- Name: species_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.species_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.species_id_seq OWNER TO postgres;

--
-- TOC entry 2014 (class 0 OID 0)
-- Dependencies: 177
-- Name: species_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.species_id_seq OWNED BY public.species.id;


--
-- TOC entry 180 (class 1259 OID 23272)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    email character varying(255),
    name character varying(255),
    pass character varying(255),
    role character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 23270)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 2015 (class 0 OID 0)
-- Dependencies: 179
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 182 (class 1259 OID 23283)
-- Name: zones; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE public.zones (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.zones OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 23281)
-- Name: zones_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.zones_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.zones_id_seq OWNER TO postgres;

--
-- TOC entry 2016 (class 0 OID 0)
-- Dependencies: 181
-- Name: zones_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.zones_id_seq OWNED BY public.zones.id;


--
-- TOC entry 1856 (class 2604 OID 23237)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.animals ALTER COLUMN id SET DEFAULT nextval('public.animals_id_seq'::regclass);


--
-- TOC entry 1857 (class 2604 OID 23245)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comments ALTER COLUMN id SET DEFAULT nextval('public.comments_id_seq'::regclass);


--
-- TOC entry 1858 (class 2604 OID 23256)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.replies ALTER COLUMN id SET DEFAULT nextval('public.replies_id_seq'::regclass);


--
-- TOC entry 1859 (class 2604 OID 23267)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.species ALTER COLUMN id SET DEFAULT nextval('public.species_id_seq'::regclass);


--
-- TOC entry 1860 (class 2604 OID 23275)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 1861 (class 2604 OID 23286)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zones ALTER COLUMN id SET DEFAULT nextval('public.zones_id_seq'::regclass);


--
-- TOC entry 1991 (class 0 OID 23234)
-- Dependencies: 172
-- Data for Name: animals; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.animals (id, name, species_id, zone_id) FROM stdin;
1	Arto	1	1
2	Ana	2	1
\.


--
-- TOC entry 2017 (class 0 OID 0)
-- Dependencies: 171
-- Name: animals_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.animals_id_seq', 2, true);


--
-- TOC entry 1993 (class 0 OID 23242)
-- Dependencies: 174
-- Data for Name: comments; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.comments (id, animal_id, author, body, date) FROM stdin;
\.


--
-- TOC entry 2018 (class 0 OID 0)
-- Dependencies: 173
-- Name: comments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comments_id_seq', 1, false);


--
-- TOC entry 1995 (class 0 OID 23253)
-- Dependencies: 176
-- Data for Name: replies; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.replies (id, author, body, comment_id, date) FROM stdin;
\.


--
-- TOC entry 2019 (class 0 OID 0)
-- Dependencies: 175
-- Name: replies_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.replies_id_seq', 1, false);


--
-- TOC entry 1997 (class 0 OID 23264)
-- Dependencies: 178
-- Data for Name: species; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.species (id, name, zone_id) FROM stdin;
1	Lagarto	1
2	Iguana	1
\.


--
-- TOC entry 2020 (class 0 OID 0)
-- Dependencies: 177
-- Name: species_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.species_id_seq', 2, true);


--
-- TOC entry 1999 (class 0 OID 23272)
-- Dependencies: 180
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, email, name, pass, role) FROM stdin;
1	admin@mail.com	admin	$2a$10$QRdMVeQqb/oEKFASia50ue2s0epK5u8gacxaKJRybnDTS7VrNPDKm	ADMIN
2	employee@mail.com	employee	$2a$10$QZBGnf6QXY6G0jLQwrRoO.2qB0EiDom53NOfLFQjWP4v/2lCQKhfq	EMPLOYEE
\.


--
-- TOC entry 2021 (class 0 OID 0)
-- Dependencies: 179
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 2, true);


--
-- TOC entry 2001 (class 0 OID 23283)
-- Dependencies: 182
-- Data for Name: zones; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.zones (id, name) FROM stdin;
1	Reptiles
\.


--
-- TOC entry 2022 (class 0 OID 0)
-- Dependencies: 181
-- Name: zones_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.zones_id_seq', 1, true);


--
-- TOC entry 1863 (class 2606 OID 23239)
-- Name: animals_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.animals
    ADD CONSTRAINT animals_pkey PRIMARY KEY (id);


--
-- TOC entry 1865 (class 2606 OID 23250)
-- Name: comments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT comments_pkey PRIMARY KEY (id);


--
-- TOC entry 1867 (class 2606 OID 23261)
-- Name: replies_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.replies
    ADD CONSTRAINT replies_pkey PRIMARY KEY (id);


--
-- TOC entry 1869 (class 2606 OID 23269)
-- Name: species_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.species
    ADD CONSTRAINT species_pkey PRIMARY KEY (id);


--
-- TOC entry 1871 (class 2606 OID 23290)
-- Name: uk_29ixq8ot8e88rk6v7jpkisgr3; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.species
    ADD CONSTRAINT uk_29ixq8ot8e88rk6v7jpkisgr3 UNIQUE (name);


--
-- TOC entry 1875 (class 2606 OID 23292)
-- Name: uk_9vf2c47kjchldfq92cptovfts; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.zones
    ADD CONSTRAINT uk_9vf2c47kjchldfq92cptovfts UNIQUE (name);


--
-- TOC entry 1873 (class 2606 OID 23280)
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 1877 (class 2606 OID 23288)
-- Name: zones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY public.zones
    ADD CONSTRAINT zones_pkey PRIMARY KEY (id);


--
-- TOC entry 1878 (class 2606 OID 23293)
-- Name: fk3pd215elkq11t9x8w7kfgeerw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.animals
    ADD CONSTRAINT fk3pd215elkq11t9x8w7kfgeerw FOREIGN KEY (zone_id) REFERENCES public.zones(id);


--
-- TOC entry 1880 (class 2606 OID 23303)
-- Name: fkbiks3hwntembg8qhubxermcvp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT fkbiks3hwntembg8qhubxermcvp FOREIGN KEY (animal_id) REFERENCES public.animals(id);


--
-- TOC entry 1882 (class 2606 OID 23313)
-- Name: fkcts4mxd6d5m1po9050rd42vl3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.species
    ADD CONSTRAINT fkcts4mxd6d5m1po9050rd42vl3 FOREIGN KEY (zone_id) REFERENCES public.zones(id);


--
-- TOC entry 1881 (class 2606 OID 23308)
-- Name: fkn0xus92n25hvud6dlfni0ttqg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.replies
    ADD CONSTRAINT fkn0xus92n25hvud6dlfni0ttqg FOREIGN KEY (comment_id) REFERENCES public.comments(id);


--
-- TOC entry 1879 (class 2606 OID 23298)
-- Name: fknxlqihrb0istc91w7yvkm7dr1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.animals
    ADD CONSTRAINT fknxlqihrb0istc91w7yvkm7dr1 FOREIGN KEY (species_id) REFERENCES public.species(id);


--
-- TOC entry 2009 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2023-09-19 15:22:13

--
-- PostgreSQL database dump complete
--

