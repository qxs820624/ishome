CREATE TABLE mdd_ppgysxx
(
    puk VARCHAR(24) DEFAULT '0' NOT NULL,
    k01_gyid VARCHAR(24) NOT NULL,
    k02_qylb VARCHAR(20) NOT NULL,
    k03_djbs VARCHAR(20),
    f01 VARCHAR(80),
    f02 VARCHAR(80),
    f03 VARCHAR(20),
    f04 VARCHAR(20),
    f05 VARCHAR(20),
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
    eb5 VARCHAR(24),
    ggg VARCHAR(24) DEFAULT 'SYSTEM',
    ppp VARCHAR(24) DEFAULT 'SYSTEM',
    ddd VARCHAR(4) DEFAULT '0' NOT NULL,
    cc1 VARCHAR(24) DEFAULT '2014/2/8' NOT NULL,
    cc2 VARCHAR(24) DEFAULT 'SYSTEM' NOT NULL,
    uu1 VARCHAR(24),
    uu2 VARCHAR(24),
PRIMARY KEY (puk , k01_gyid , k02_qylb)
)
