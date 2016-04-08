﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TheSeed
{
    class ConfigUtils
    {
        #region 系统运行参数
        /// <summary>
        /// 用户登录状态
        /// </summary>
        public static Boolean Login { get; set; }
        /// <summary>
        /// 系统网络状态
        /// </summary>
        public static Boolean NetConnect { get; set; }

        /// <summary>
        /// 管理员权限
        /// </summary>
        public static Boolean AdminRool { get; set; }
        /// <summary>
        /// 主服务器地址
        /// </summary>
        public static Boolean FirstServerAdress { get; set; }
        /// <summary>
        /// 备份服务器地址
        /// </summary>
        public static Boolean SecondServerAdress { get; set; }
        /// <summary>
        /// 本地数据储存路径
        /// </summary>
        public static Boolean SavePath { get; set; }
        #endregion

        #region 用户个人参数
        /// <summary>
        /// 我的关注<MyOrderResource><MyOrderSeries>
        /// </summary>
        public static List<String> MyOrder { get; set; }
        #endregion

        //config/conf.dat
        public static Boolean LoadConfigFile()
        {
            return true;
        }
        public static Boolean LoadBaseConfig()
        {
            return true;
        }
        public static Boolean LoadMyConfig()
        {
            return true;
        }
    }
}