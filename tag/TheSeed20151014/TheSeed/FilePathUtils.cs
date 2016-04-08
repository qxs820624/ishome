﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TheSeed
{
    /// <summary>
    /// 文件存储路径
    /// </summary>
    class FilePathUtils
    {
        #region 本地路径
        /// <summary>
        /// 系统配置
        /// </summary>
        public static String LOCAL_CFG = "//cfg";
        /// <summary>
        /// 运行参数
        /// </summary>
        public static String LOCAL_CFG_SYS = LOCAL_CFG + "//sys";
        /// <summary>
        /// 个人参数
        /// </summary>
        public static String LOCAL_CFG_USR = LOCAL_CFG + "//usr";
        /// <summary>
        /// 资源路径
        /// </summary>
        public static String LOCAL_RES = "res";
        /// <summary>
        /// 剧集路径
        /// </summary>
        public static String LOCAL_SER = "ser";
        #endregion


        #region 云端路径
        /// <summary>
        /// 系统配置
        /// </summary>
        public static String SERVER_ATR = "//ATR";
        /// <summary>
        /// 服务器
        /// </summary>
        public static String SERVER_ATR_SRV = SERVER_ATR + "//SRV";
        /// <summary>
        /// 语言
        /// </summary>
        public static String SERVER_ATR_YY = SERVER_ATR + "//YY";
        /// <summary>
        /// 地区
        /// </summary>
        public static String SERVER_ATR_DQ = SERVER_ATR + "//DQ";
        /// <summary>
        /// 分类
        /// </summary>
        public static String SERVER_ATR_FL = SERVER_ATR + "//FL";
        /// <summary>
        /// 字幕
        /// </summary>
        public static String SERVER_ATR_ZM = SERVER_ATR + "//ZM";
        /// <summary>
        /// 资源路径
        /// </summary>
        public static String SERVER_RES = "RES";
        /// <summary>
        /// 剧集路径
        /// </summary>
        public static String SERVER_SER = "SER";
        #endregion


    }
}