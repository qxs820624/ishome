CREATE TABLE md7_cpggxx
(
    puk VARCHAR(24) DEFAULT '0' NOT NULL,
    k01_cpid VARCHAR(24) NOT NULL,
    k02_cpggtm VARCHAR(40),
    f01_ggqc VARCHAR(80),
    f02_ggqcpym VARCHAR(40),
    f03_cpzbh VARCHAR(80),
    f04_cpcbh VARCHAR(80),
    f05_cpxh VARCHAR(20),
    f06_zwggms VARCHAR(80),
    f07_ywggms VARCHAR(80),
    f08_cl VARCHAR(80),
    f09_ys VARCHAR(20),
    f10_wc VARCHAR(20),
    f11_jldw VARCHAR(20),
    f12_cd VARCHAR(20),
    f13_kd VARCHAR(20),
    f14_gd VARCHAR(20),
    f15_hd VARCHAR(20),
    f16_nzj VARCHAR(20),
    f17_wzj VARCHAR(20),
    f18_ks VARCHAR(20),
    f19_zl VARCHAR(20),
    f20_xz VARCHAR(20),
    f21_syhc VARCHAR(80),
    f22_syfw VARCHAR(80),
    f23 VARCHAR(80),
    f24 VARCHAR(80),
    f25 VARCHAR(80),
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
    ggg TEXT,
    ppp VARCHAR(24) DEFAULT 'SYSTEM',
    ddd VARCHAR(4) DEFAULT '0' NOT NULL,
    cc1 VARCHAR(24) DEFAULT '2014/2/8' NOT NULL,
    cc2 VARCHAR(24) DEFAULT 'SYSTEM' NOT NULL,
    uu1 VARCHAR(24),
    uu2 VARCHAR(24),
PRIMARY KEY (puk , k01_cpid)
)
