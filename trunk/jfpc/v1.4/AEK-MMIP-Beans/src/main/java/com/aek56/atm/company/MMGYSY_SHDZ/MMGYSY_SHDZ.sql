CREATE TABLE mmgysy_shdz
(
    puk VARCHAR(24) DEFAULT '0' NOT NULL,
    k01_gysid VARCHAR(24),
    k02_mrshdz VARCHAR(20) DEFAULT '0',
    f01_shrxm VARCHAR(40),
    f02_gddh VARCHAR(40),
    f03_yddh VARCHAR(80),
    f04_szs VARCHAR(40),
    f05_szs VARCHAR(40),
    f06_szq VARCHAR(40),
    f07_szxxdz VARCHAR(200),
    bbb VARCHAR(80),
    fb1 VARCHAR(40),
    fb2 VARCHAR(80),
    fb3 VARCHAR(20),
    fb4 VARCHAR(20),
    fb5 VARCHAR(20),
    eb1 VARCHAR(40),
    eb2 VARCHAR(80),
    eb3 VARCHAR(20),
    eb4 VARCHAR(20),
    eb5 VARCHAR(24) DEFAULT 'SYSTEM',
    ggg VARCHAR(24) DEFAULT 'SYSTEM',
    ppp VARCHAR(24) DEFAULT 'SYSTEM',
    ddd VARCHAR(4) DEFAULT '0' NOT NULL,
    cc1 VARCHAR(24) DEFAULT '2014/2/8' NOT NULL,
    cc2 VARCHAR(24) DEFAULT 'SYSTEM' NOT NULL,
    uu1 VARCHAR(24),
    uu2 VARCHAR(24),
PRIMARY KEY (puk)
)
