----------------------------------------------------------------------
----------------------------------------------------------------------
--------------------------TABELAS-------------------------------------
----------------------------------------------------------------------
----------------------------------------------------------------------
CREATE SEQUENCE public.tipo_container_cod_tipo_container_seq;

CREATE TABLE public.tipo_container (
                cod_tipo_container INTEGER NOT NULL DEFAULT nextval('public.tipo_container_cod_tipo_container_seq'),
                nome_tipo_container VARCHAR NOT NULL,
                CONSTRAINT tipo_container_pk PRIMARY KEY (cod_tipo_container)
);


ALTER SEQUENCE public.tipo_container_cod_tipo_container_seq OWNED BY public.tipo_container.cod_tipo_container;

CREATE SEQUENCE public.evento_origem_cod_evento_origem_seq;

CREATE TABLE public.evento_origem (
                cod_evento_origem INTEGER NOT NULL DEFAULT nextval('public.evento_origem_cod_evento_origem_seq'),
                nome_evento_origem VARCHAR NOT NULL,
                CONSTRAINT cod_evento_origem PRIMARY KEY (cod_evento_origem)
);


ALTER SEQUENCE public.evento_origem_cod_evento_origem_seq OWNED BY public.evento_origem.cod_evento_origem;

CREATE SEQUENCE public.tipo_coletor_cod_tipo_coletor_seq;

CREATE TABLE public.tipo_coletor (
                cod_tipo_coletor INTEGER NOT NULL DEFAULT nextval('public.tipo_coletor_cod_tipo_coletor_seq'),
                nome_tipo_coletor VARCHAR NOT NULL,
                CONSTRAINT cod_tipo_coletor PRIMARY KEY (cod_tipo_coletor)
);


ALTER SEQUENCE public.tipo_coletor_cod_tipo_coletor_seq OWNED BY public.tipo_coletor.cod_tipo_coletor;

CREATE SEQUENCE public.destinacao_cod_destinacao_seq;

CREATE TABLE public.destinacao (
                cod_destinacao INTEGER NOT NULL DEFAULT nextval('public.destinacao_cod_destinacao_seq'),
                nome_destinacao VARCHAR NOT NULL,
                CONSTRAINT cod_destinacao PRIMARY KEY (cod_destinacao)
);


ALTER SEQUENCE public.destinacao_cod_destinacao_seq OWNED BY public.destinacao.cod_destinacao;

CREATE SEQUENCE public.tecnologia_cod_tecnologia_seq_1;

CREATE TABLE public.tecnologia (
                cod_tecnologia INTEGER NOT NULL DEFAULT nextval('public.tecnologia_cod_tecnologia_seq_1'),
                nome_tecnologia VARCHAR NOT NULL,
                CONSTRAINT cod_tecnologia PRIMARY KEY (cod_tecnologia)
);


ALTER SEQUENCE public.tecnologia_cod_tecnologia_seq_1 OWNED BY public.tecnologia.cod_tecnologia;

CREATE SEQUENCE public.tipo_item_cod_tipo_seq;

CREATE TABLE public.tipo_item (
                cod_tipo INTEGER NOT NULL DEFAULT nextval('public.tipo_item_cod_tipo_seq'),
                nome_tipo VARCHAR NOT NULL,
                CONSTRAINT cod_tipo PRIMARY KEY (cod_tipo)
);


ALTER SEQUENCE public.tipo_item_cod_tipo_seq OWNED BY public.tipo_item.cod_tipo;

CREATE SEQUENCE public.modelo_cod_modelo_seq_1;

CREATE TABLE public.modelo (
                cod_modelo INTEGER NOT NULL DEFAULT nextval('public.modelo_cod_modelo_seq_1'),
                nome_modelo VARCHAR NOT NULL,
                CONSTRAINT cod_modelo PRIMARY KEY (cod_modelo)
);


ALTER SEQUENCE public.modelo_cod_modelo_seq_1 OWNED BY public.modelo.cod_modelo;

CREATE SEQUENCE public.interface_cod_interface_seq_1;

CREATE TABLE public.interface (
                cod_interface INTEGER NOT NULL DEFAULT nextval('public.interface_cod_interface_seq_1'),
                nome_interface VARCHAR NOT NULL,
                CONSTRAINT cod_interface PRIMARY KEY (cod_interface)
);


ALTER SEQUENCE public.interface_cod_interface_seq_1 OWNED BY public.interface.cod_interface;

CREATE SEQUENCE public.marca_cod_marca_seq_1;

CREATE TABLE public.marca (
                cod_marca INTEGER NOT NULL DEFAULT nextval('public.marca_cod_marca_seq_1'),
                nome_marca VARCHAR NOT NULL,
                CONSTRAINT cod_marca PRIMARY KEY (cod_marca)
);


ALTER SEQUENCE public.marca_cod_marca_seq_1 OWNED BY public.marca.cod_marca;

CREATE SEQUENCE public.coletor_cod_coletor_seq;

CREATE TABLE public.coletor (
                cod_coletor INTEGER NOT NULL DEFAULT nextval('public.coletor_cod_coletor_seq'),
                cod_tipo_coletor INTEGER NOT NULL,
                nome_coletor VARCHAR NOT NULL,
                CONSTRAINT cod_coletor PRIMARY KEY (cod_coletor)
);


ALTER SEQUENCE public.coletor_cod_coletor_seq OWNED BY public.coletor.cod_coletor;

CREATE SEQUENCE public.doador_cod_doador_seq_1;

CREATE TABLE public.doador (
                cod_doador INTEGER NOT NULL DEFAULT nextval('public.doador_cod_doador_seq_1'),
                nome_doador VARCHAR NOT NULL,
                CONSTRAINT cod_doador PRIMARY KEY (cod_doador)
);


ALTER SEQUENCE public.doador_cod_doador_seq_1 OWNED BY public.doador.cod_doador;

CREATE SEQUENCE public.container_cod_container_seq;

CREATE TABLE public.container (
                cod_container INTEGER NOT NULL DEFAULT nextval('public.container_cod_container_seq'),
                cod_tipo_container INTEGER NOT NULL,
                localizacao_container VARCHAR NOT NULL,
                CONSTRAINT cod_container PRIMARY KEY (cod_container)
);


ALTER SEQUENCE public.container_cod_container_seq OWNED BY public.container.cod_container;

CREATE SEQUENCE public.usuario_cod_usuario_seq;

CREATE TABLE public.usuario (
                cod_usuario INTEGER NOT NULL DEFAULT nextval('public.usuario_cod_usuario_seq'),
                senha_usuario VARCHAR NOT NULL,
                nome_usuario VARCHAR NOT NULL,
                usuario_administrador BOOLEAN NOT NULL,
                chave_encriptacao VARCHAR NOT NULL,
                email VARCHAR NOT NULL,
                registro_academico VARCHAR NOT NULL,
                CONSTRAINT cod_usuario PRIMARY KEY (cod_usuario)
);


ALTER SEQUENCE public.usuario_cod_usuario_seq OWNED BY public.usuario.cod_usuario;

CREATE SEQUENCE public.item_acervo_cod_item_acervo_seq_2_1;

CREATE TABLE public.item_acervo (
                cod_item_acervo INTEGER NOT NULL DEFAULT nextval('public.item_acervo_cod_item_acervo_seq_2_1'),
                cod_doador INTEGER NOT NULL,
                cod_usuario INTEGER NOT NULL,
                cod_tipo INTEGER NOT NULL,
                cod_marca INTEGER NOT NULL,
                cod_modelo INTEGER NOT NULL,
                cod_interface INTEGER NOT NULL,
                cod_tecnologia INTEGER NOT NULL,
                cod_container INTEGER NOT NULL,
                data_cadastro_item_acervo DATE NOT NULL,
                descricao_item_acervo VARCHAR NOT NULL,
                capacidade_mb INTEGER,
                ano INTEGER NOT NULL,
                funciona BOOLEAN NOT NULL,
                CONSTRAINT cod_item_acervo PRIMARY KEY (cod_item_acervo)
);


ALTER SEQUENCE public.item_acervo_cod_item_acervo_seq_2_1 OWNED BY public.item_acervo.cod_item_acervo;

CREATE SEQUENCE public.imagem_cod_imagem_seq;

CREATE TABLE public.imagem (
                cod_imagem INTEGER NOT NULL DEFAULT nextval('public.imagem_cod_imagem_seq'),
                cod_item_acervo INTEGER NOT NULL,
                link VARCHAR NOT NULL,
                CONSTRAINT cod_imagem PRIMARY KEY (cod_imagem, cod_item_acervo)
);


ALTER SEQUENCE public.imagem_cod_imagem_seq OWNED BY public.imagem.cod_imagem;

CREATE SEQUENCE public.repasse_cod_repasse_seq;

CREATE TABLE public.repasse (
                cod_repasse INTEGER NOT NULL DEFAULT nextval('public.repasse_cod_repasse_seq'),
                cod_usuario INTEGER NOT NULL,
                cod_coletor INTEGER NOT NULL,
                cod_destinacao INTEGER NOT NULL,
                data_repasse DATE NOT NULL,
                CONSTRAINT cod_repasse PRIMARY KEY (cod_repasse)
);


ALTER SEQUENCE public.repasse_cod_repasse_seq OWNED BY public.repasse.cod_repasse;

CREATE SEQUENCE public.item_repasse_cod_item_repasse_seq;

CREATE TABLE public.item_repasse (
                cod_item_repasse INTEGER NOT NULL DEFAULT nextval('public.item_repasse_cod_item_repasse_seq'),
                cod_repasse INTEGER NOT NULL,
                cod_tipo INTEGER NOT NULL,
                quantidade_item_repasse INTEGER NOT NULL,
                CONSTRAINT cod_item_repasse PRIMARY KEY (cod_item_repasse, cod_repasse)
);


ALTER SEQUENCE public.item_repasse_cod_item_repasse_seq OWNED BY public.item_repasse.cod_item_repasse;

CREATE SEQUENCE public.doacao_cod_doacao_seq;

CREATE TABLE public.doacao (
                cod_doacao INTEGER NOT NULL DEFAULT nextval('public.doacao_cod_doacao_seq'),
                cod_usuario INTEGER NOT NULL,
                cod_doador INTEGER NOT NULL,
                cod_evento_origem INTEGER NOT NULL,
                data_doacao DATE NOT NULL,
                CONSTRAINT cod_doacao PRIMARY KEY (cod_doacao)
);


ALTER SEQUENCE public.doacao_cod_doacao_seq OWNED BY public.doacao.cod_doacao;

CREATE SEQUENCE public.item_doacao_cod_item_doacao_seq;

CREATE TABLE public.item_doacao (
                cod_item_doacao INTEGER NOT NULL DEFAULT nextval('public.item_doacao_cod_item_doacao_seq'),
                cod_doacao INTEGER NOT NULL,
                cod_tipo INTEGER NOT NULL,
                quantidade_item_doacao INTEGER NOT NULL,
                CONSTRAINT cod_item_doacao PRIMARY KEY (cod_item_doacao, cod_doacao)
);


ALTER SEQUENCE public.item_doacao_cod_item_doacao_seq OWNED BY public.item_doacao.cod_item_doacao;

ALTER TABLE public.container ADD CONSTRAINT tipo_container_container_fk
FOREIGN KEY (cod_tipo_container)
REFERENCES public.tipo_container (cod_tipo_container)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.doacao ADD CONSTRAINT evento_origem_doacao_fk
FOREIGN KEY (cod_evento_origem)
REFERENCES public.evento_origem (cod_evento_origem)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.coletor ADD CONSTRAINT tipo_coletor_coletor_fk
FOREIGN KEY (cod_tipo_coletor)
REFERENCES public.tipo_coletor (cod_tipo_coletor)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.repasse ADD CONSTRAINT destinacao_repasse_fk
FOREIGN KEY (cod_destinacao)
REFERENCES public.destinacao (cod_destinacao)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.item_acervo ADD CONSTRAINT tecnologia_item_acervo_fk
FOREIGN KEY (cod_tecnologia)
REFERENCES public.tecnologia (cod_tecnologia)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.item_acervo ADD CONSTRAINT tipo_item_acervo_item_acervo_fk
FOREIGN KEY (cod_tipo)
REFERENCES public.tipo_item (cod_tipo)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.item_doacao ADD CONSTRAINT tipo_item_doacao_fk
FOREIGN KEY (cod_tipo)
REFERENCES public.tipo_item (cod_tipo)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.item_repasse ADD CONSTRAINT tipo_item_repasse_fk
FOREIGN KEY (cod_tipo)
REFERENCES public.tipo_item (cod_tipo)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.item_acervo ADD CONSTRAINT modelo_item_acervo_fk
FOREIGN KEY (cod_modelo)
REFERENCES public.modelo (cod_modelo)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.item_acervo ADD CONSTRAINT interface_item_acervo_fk
FOREIGN KEY (cod_interface)
REFERENCES public.interface (cod_interface)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.item_acervo ADD CONSTRAINT marca_item_acervo_fk
FOREIGN KEY (cod_marca)
REFERENCES public.marca (cod_marca)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.repasse ADD CONSTRAINT coletor_repasse_fk
FOREIGN KEY (cod_coletor)
REFERENCES public.coletor (cod_coletor)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.doacao ADD CONSTRAINT doador_doacao_fk
FOREIGN KEY (cod_doador)
REFERENCES public.doador (cod_doador)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.item_acervo ADD CONSTRAINT doador_item_acervo_fk
FOREIGN KEY (cod_doador)
REFERENCES public.doador (cod_doador)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.item_acervo ADD CONSTRAINT container_item_acervo_fk
FOREIGN KEY (cod_container)
REFERENCES public.container (cod_container)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.repasse ADD CONSTRAINT usuario_repasse_fk
FOREIGN KEY (cod_usuario)
REFERENCES public.usuario (cod_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.doacao ADD CONSTRAINT usuario_doacao_fk
FOREIGN KEY (cod_usuario)
REFERENCES public.usuario (cod_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.item_acervo ADD CONSTRAINT usuario_item_acervo_fk
FOREIGN KEY (cod_usuario)
REFERENCES public.usuario (cod_usuario)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.imagem ADD CONSTRAINT item_acervo_imagem_fk
FOREIGN KEY (cod_item_acervo)
REFERENCES public.item_acervo (cod_item_acervo)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.item_repasse ADD CONSTRAINT repasse_item_repasse_fk
FOREIGN KEY (cod_repasse)
REFERENCES public.repasse (cod_repasse)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.item_doacao ADD CONSTRAINT doacao_item_doacao_fk
FOREIGN KEY (cod_doacao)
REFERENCES public.doacao (cod_doacao)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;


----------------------------------------------------------------------
----------------------------------------------------------------------
--------------------------VIEWS---------------------------------------
----------------------------------------------------------------------
----------------------------------------------------------------------


-- DROP VIEW acervo_detalhado;

CREATE OR REPLACE VIEW acervo_detalhado AS 
 SELECT item_acervo.cod_item_acervo AS "Código de Item do Acervo", 
    usuario.nome_usuario AS "Usuário", doador.nome_doador AS "Doador", 
    item_acervo.data_cadastro_item_acervo AS "Data", 
    tipo_item.nome_tipo AS "Tipo", marca.nome_marca AS "Marca", 
    modelo.nome_modelo AS "Modelo", interface.nome_interface AS "Interface", 
    tecnologia.nome_tecnologia AS "Tecnologia", 
    item_acervo.capacidade_mb AS "Capacidade", item_acervo.ano AS "Ano", 
    item_acervo.descricao_item_acervo AS "Descrição", 
    item_acervo.funciona AS "Funciona",
	item_acervo.cod_container AS "Código de Container"
   FROM item_acervo, usuario, doador, tipo_item, marca, modelo, interface, 
    tecnologia
  WHERE item_acervo.cod_usuario = usuario.cod_usuario
	AND item_acervo.cod_doador = doador.cod_doador
	AND item_acervo.cod_tipo = tipo_item.cod_tipo
	AND item_acervo.cod_marca = marca.cod_marca
	AND item_acervo.cod_modelo = modelo.cod_modelo
	AND item_acervo.cod_interface = interface.cod_interface
	AND item_acervo.cod_tecnologia = tecnologia.cod_tecnologia;

-- View: imagem_detalhado

-- DROP VIEW imagem_detalhado;
CREATE OR REPLACE VIEW imagem_detalhado AS
	SELECT imagem.cod_imagem as "Código de Imagem",
		item_acervo.cod_item_acervo as "Código de Item Acervo",
		marca.nome_marca as "Marca",
		modelo.nome_modelo as "Modelo",
		imagem.link as "Caminho/Link da Imagem"
	FROM item_acervo,imagem,modelo,marca
	WHERE imagem.cod_item_acervo=item_acervo.cod_item_acervo
		AND item_acervo.cod_marca=marca.cod_marca
		AND item_acervo.cod_modelo=modelo.cod_modelo;


-- View: coletor_detalhado

-- DROP VIEW coletor_detalhado;

CREATE OR REPLACE VIEW coletor_detalhado AS 
 SELECT coletor.cod_coletor AS "Código do Coletor", 
    coletor.nome_coletor AS "Nome do Coletor", 
    tipo_coletor.nome_tipo_coletor AS "Tipo do Coletor"
   FROM coletor, tipo_coletor
  WHERE coletor.cod_tipo_coletor = tipo_coletor.cod_tipo_coletor;

-- View: container_detalhado

-- DROP VIEW container_detalhado;

CREATE OR REPLACE VIEW container_detalhado AS 
 SELECT container.cod_container AS "Código do Container", 
    container.localizacao_container AS "Localização do Container",
	tipo_container.nome_tipo_container AS "Tipo do Container"
   FROM container, tipo_container
  WHERE container.cod_tipo_container = tipo_container.cod_tipo_container;

-- View: doacao_detalhado

-- DROP VIEW doacao_detalhado;

CREATE OR REPLACE VIEW doacao_detalhado AS 
 SELECT doacao.cod_doacao AS "Código de Doação", 
    usuario.nome_usuario AS "Usuário", doador.nome_doador AS "Doador", 
    evento_origem.nome_evento_origem AS "Evento de Origem", 
    doacao.data_doacao AS "Data"
   FROM doacao, doador, evento_origem, usuario
  WHERE doador.cod_doador = doacao.cod_doador
	AND usuario.cod_usuario = doacao.cod_usuario
	AND doacao.cod_evento_origem = evento_origem.cod_evento_origem;

-- View: doador_detalhado

-- DROP VIEW doador_detalhado;

CREATE OR REPLACE VIEW doador_detalhado AS 
 SELECT doador.cod_doador AS "Código do Doador", 
    doador.nome_doador AS "Nome do Doador"
   FROM doador;



-- View: item_doacao_detalhado

-- DROP VIEW item_doacao_detalhado;

CREATE OR REPLACE VIEW item_doacao_detalhado AS 
 SELECT item_doacao.cod_item_doacao AS "Código de Item-Doação", 
	doacao.cod_doacao as "Código de Doação",
    usuario.nome_usuario AS "Usuário",
	doador.nome_doador AS "Doador", 
    evento_origem.nome_evento_origem AS "Origem", 
    tipo_item.nome_tipo AS "Tipo de Item", 
    item_doacao.quantidade_item_doacao AS "Quantidade", 
    doacao.data_doacao AS "Data"
   FROM item_doacao, doador, evento_origem, usuario, tipo_item, doacao
  WHERE doacao.cod_usuario = usuario.cod_usuario
	AND item_doacao.cod_doacao = doacao.cod_doacao
	AND doacao.cod_evento_origem = evento_origem.cod_evento_origem
	AND doacao.cod_doador = doador.cod_doador
	AND item_doacao.cod_tipo = tipo_item.cod_tipo;


-- View: item_repasse_detalhado

-- DROP VIEW item_repasse_detalhado;

CREATE OR REPLACE VIEW item_repasse_detalhado AS 
 SELECT item_repasse.cod_item_repasse AS "Código do Item-Repasse",
	repasse.cod_repasse as "Codigo do Repasse",
    usuario.nome_usuario AS "Usuário", coletor.nome_coletor AS "Coletor", 
    destinacao.nome_destinacao AS "Destinação", 
    tipo_item.nome_tipo AS "Tipo de Item", 
    item_repasse.quantidade_item_repasse AS "Quantidade", 
    repasse.data_repasse AS "Data"
   FROM item_repasse, coletor, destinacao, usuario, tipo_item, repasse
  WHERE repasse.cod_usuario = usuario.cod_usuario
	AND item_repasse.cod_repasse = repasse.cod_repasse
	AND repasse.cod_coletor = coletor.cod_coletor
	AND item_repasse.cod_tipo = tipo_item.cod_tipo
	AND repasse.cod_destinacao = destinacao.cod_destinacao;


-- View: repasse_detalhado

-- DROP VIEW repasse_detalhado;

CREATE OR REPLACE VIEW repasse_detalhado AS 
 SELECT repasse.cod_repasse AS "Código do Repasse", 
    usuario.nome_usuario AS "Usuário", coletor.nome_coletor AS "Coletor", 
    tipo_coletor.nome_tipo_coletor AS "Tipo de Coletor", 
    destinacao.nome_destinacao AS "Destinação", 
    repasse.data_repasse AS "Data"
   FROM repasse, coletor, tipo_coletor, destinacao, usuario
  WHERE repasse.cod_coletor = coletor.cod_coletor
	AND repasse.cod_usuario = usuario.cod_usuario
	AND coletor.cod_coletor = tipo_coletor.cod_tipo_coletor
	AND repasse.cod_destinacao = destinacao.cod_destinacao;


-- View: soma_tipos_doacao

-- DROP VIEW soma_tipos_doacao;

CREATE OR REPLACE VIEW soma_tipos_doacao AS 
 SELECT item_doacao.cod_tipo, sum(item_doacao.quantidade_item_doacao) AS qtdd
   FROM item_doacao
  GROUP BY item_doacao.cod_tipo;
  
-- View: soma_tipos_repasse

-- DROP VIEW soma_tipos_repasse;

CREATE OR REPLACE VIEW soma_tipos_repasse AS 
 SELECT item_repasse.cod_tipo, 
    sum(item_repasse.quantidade_item_repasse) AS qtdr
   FROM item_repasse
  GROUP BY item_repasse.cod_tipo;


-- View: usuario_detalhado

-- DROP VIEW usuario_detalhado;

CREATE OR REPLACE VIEW usuario_detalhado AS 
 SELECT usuario.cod_usuario AS "Código de Usuário", 
    usuario.registro_academico as "R.A.",
	usuario.nome_usuario AS "Nome do Usuário", 
    usuario.usuario_administrador AS "Administrador",
	usuario.email as "E-mail"
   FROM usuario;

-- View: estoque

-- DROP VIEW estoque;

CREATE OR REPLACE VIEW estoque AS 
 SELECT a.cod_tipo, a.qtdd, b.qtdr, 
        CASE
            WHEN b.cod_tipo IS NULL THEN a.qtdd
            ELSE a.qtdd - b.qtdr
        END AS saldo
   FROM soma_tipos_doacao a
   LEFT JOIN soma_tipos_repasse b ON a.cod_tipo = b.cod_tipo;

-- View: estoque_detalhado

-- DROP VIEW estoque_detalhado;

CREATE OR REPLACE VIEW estoque_detalhado AS 
 SELECT estoque.cod_tipo AS "Código de Tipo", tipo_item.nome_tipo AS "Tipo", 
    estoque.qtdd AS "Quantidade de Doações", 
    estoque.qtdr AS "Quantidade de Repasses", estoque.saldo AS "Saldo"
   FROM estoque, tipo_item
  WHERE estoque.cod_tipo = tipo_item.cod_tipo;

----------------------------------------------------------------------
----------------------------------------------------------------------
--------------------------INDEXES-------------------------------------
----------------------------------------------------------------------
----------------------------------------------------------------------
  
CREATE INDEX i_nome_coletor ON coletor(nome_coletor);
CREATE INDEX i_localizacao_container ON container(localizacao_container);
CREATE INDEX i_nome_destinacao ON destinacao(nome_destinacao);
CREATE INDEX i_nome_doador ON doador(nome_doador);
CREATE INDEX i_nome_evento_origem ON evento_origem(nome_evento_origem);
CREATE INDEX i_nome_interface ON interface(nome_interface);
CREATE INDEX i_nome_marca ON marca(nome_marca);
CREATE INDEX i_nome_modelo ON modelo(nome_modelo);
CREATE INDEX i_nome_tecnologia ON tecnologia(nome_tecnologia);
CREATE INDEX i_nome_tipo_coletor ON tipo_coletor(nome_tipo_coletor);
CREATE INDEX i_nome_tipo_container ON tipo_container(nome_tipo_container);
CREATE INDEX i_nome_tipo_item ON tipo_item(nome_tipo);
CREATE INDEX i_nome_usuario ON usuario(nome_usuario);


----------------------------------------------------------------------
----------------------------------------------------------------------
--------------------------TRIGGERS------------------------------------
----------------------------------------------------------------------
----------------------------------------------------------------------

--TRIGGER EXCLUI ITEM DOACAO DEVIDO QUANTIDADE INVALIDA
CREATE OR REPLACE FUNCTION exclui_item_doacao()
  RETURNS trigger AS
$BODY$
 BEGIN

 DELETE FROM item_doacao WHERE cod_doacao = OLD.cod_doacao;
 RETURN OLD;
 END;

 $BODY$
  LANGUAGE plpgsql VOLATILE;
  
  CREATE TRIGGER quantidade_invalida_doacao
  BEFORE DELETE
  ON doacao
  FOR EACH ROW
  EXECUTE PROCEDURE exclui_item_doacao();

  
  --QUANTIDADE INVALIDA DOACAO
  
  CREATE OR REPLACE FUNCTION quantidade_invalida_doacao()
  RETURNS trigger AS
$BODY$ 
	BEGIN
       
        IF NEW.quantidade_item_doacao < 1  THEN
            RAISE EXCEPTION '% O VALOR DE QUANTIDADE NÃO PODE SER MENOR QUE 1: ', NEW.quantidade_item_doacao;
        
        ELSIF (true) THEN RETURN NEW; 
        END IF;        
        END;
        
$BODY$
  LANGUAGE plpgsql VOLATILE;
  
  CREATE TRIGGER quantidade_invalida_doacao
  BEFORE INSERT OR UPDATE
  ON item_doacao
  FOR EACH ROW
  EXECUTE PROCEDURE quantidade_invalida_doacao();
  
  
  
  --QUANTIDADE INVALIDA ITEM REPASSE
  CREATE OR REPLACE FUNCTION quantidade_invalida_repasse()
  RETURNS trigger AS
$BODY$ 
	BEGIN
       
        IF NEW.quantidade_item_repasse < 1  THEN
            RAISE EXCEPTION '% O VALOR DE QUANTIDADE NÃO PODE SER MENOR QUE 1: ', NEW.quantidade_item_repasse;
        
        ELSIF (true) THEN RETURN NEW; 
        END IF;        
        END;
        
$BODY$
  LANGUAGE plpgsql VOLATILE;
  
  CREATE TRIGGER quantidade_invalida_repasse
  BEFORE INSERT OR UPDATE
  ON item_repasse
  FOR EACH ROW
  EXECUTE PROCEDURE quantidade_invalida_repasse();
  
  
  
  --EXCLUI ITEM DOACAO POR QUANTIDADE INVALIDA
  CREATE OR REPLACE FUNCTION exclui_item_doacao()
  RETURNS trigger AS
$BODY$
 BEGIN

 DELETE FROM item_doacao WHERE cod_doacao = OLD.cod_doacao;
 RETURN OLD;
 END;

 $BODY$
 LANGUAGE plpgsql VOLATILE;
 
 CREATE TRIGGER excluir_item_doacao
  BEFORE DELETE
  ON doacao
  FOR EACH ROW
  EXECUTE PROCEDURE exclui_item_doacao();

  
  
  
  --EXCLUI ITEM REPASSE POR QUANTIDADE INVALIDA
  CREATE OR REPLACE FUNCTION exclui_item_repasse()
  RETURNS trigger AS
$BODY$
 BEGIN

 DELETE FROM item_repasse WHERE cod_repasse = OLD.cod_repasse;
 RETURN OLD;
 END;

 $BODY$
  LANGUAGE plpgsql VOLATILE;
    
  CREATE TRIGGER excluir_item_repasse
  BEFORE DELETE
  ON repasse
  FOR EACH ROW
  EXECUTE PROCEDURE exclui_item_repasse();
  
 -- ALTERA ITEM_DOCAO APARTIR DA ALTERACAO DA DOACAO
 
CREATE OR REPLACE FUNCTION altera_item()
  RETURNS trigger AS
$BODY$
 BEGIN

UPDATE item_doacao SET cod_doador=NEW.cod_doador
WHERE NEW.cod_doacao=item_doacao.cod_doacao;
RETURN NEW;
END;
 
  $BODY$
  LANGUAGE plpgsql VOLATILE;
  
  CREATE TRIGGER altera_item
  BEFORE UPDATE
  ON doacao
  FOR EACH ROW
  EXECUTE PROCEDURE altera_item();

 -- VERIFICA ESTOQUE ANTES DE ADICIONAR OU ATUALIZAR O ITEM_REPASSE



CREATE OR REPLACE FUNCTION verifica_estoque() 
RETURNS trigger AS
$BODY$
BEGIN
IF((select quantidade_item_doacao from item_doacao where item_doacao.cod_tipo=NEW.cod_tipo) < NEW.quantidade_item_repasse) THEN
	RAISE EXCEPTION 'NAO HA ESTOQUE SUFICIENTE PARA FAZER REPASSE DESSE ITEM, cod_tipo: %',NEW.cod_tipo; 
	RETURN NULL;
END IF;
END;

$BODY$
LANGUAGE plpgsql VOLATILE;


--DROP TRIGGER estoque ON public.item_repasse; 
CREATE TRIGGER estoque
  BEFORE INSERT OR UPDATE 
  ON item_repasse
  FOR EACH ROW
  EXECUTE PROCEDURE verifica_estoque();

----------------------------------------------------------------------
----------------------------------------------------------------------
--------------------------ITEMS---------------------------------------
----------------------------------------------------------------------
----------------------------------------------------------------------

INSERT INTO usuario(
            cod_usuario, senha_usuario, nome_usuario, usuario_administrador, 
            chave_encriptacao, email, registro_academico)
    VALUES (default, 'DktrZEVMMyD64FkGxjZIyw==', 'Administrador',true, 
            'dc34698a-f0c0-4f', 'sgacervo.uepg2016@gmail.com', 99999999);
INSERT INTO interface(cod_interface,nome_interface) values(default,'NÃO SE APLICA');
INSERT INTO tecnologia(cod_tecnologia,nome_tecnologia) values(default,'NÃO SE APLICA');

