package frc.team1918.paths;

import frc.team1918.lib.control.SwerveTrajectory;

public class RedOverBump extends Path {
   private final static double[][] points = {
       {0,0.0,0.0,0.0,0.0,-0.0,0.0},
       {0.01,0.0,-0.0,0.0,0.0187,0.013,-0.013},
       {0.02,0.0002,0.0001,-0.0001,0.0374,0.026,-0.0261},
       {0.0301,0.0006,0.0004,-0.0004,0.0561,0.0389,-0.039},
       {0.0401,0.0011,0.0008,-0.0008,0.0748,0.0517,-0.0519},
       {0.0501,0.0019,0.0013,-0.0013,0.0936,0.0645,-0.0648},
       {0.0601,0.0028,0.0019,-0.002,0.1125,0.0772,-0.0777},
       {0.0702,0.0039,0.0027,-0.0027,0.1314,0.0899,-0.0904},
       {0.0802,0.0053,0.0036,-0.0036,0.1503,0.1026,-0.1032},
       {0.0902,0.0068,0.0046,-0.0047,0.1692,0.1152,-0.1159},
       {0.1002,0.0085,0.0058,-0.0058,0.1882,0.1277,-0.1285},
       {0.1103,0.0103,0.0071,-0.0071,0.2072,0.1402,-0.1411},
       {0.1203,0.0124,0.0085,-0.0085,0.2263,0.1526,-0.1536},
       {0.1303,0.0147,0.01,-0.0101,0.2454,0.1649,-0.1661},
       {0.1403,0.0172,0.0117,-0.0117,0.2646,0.1772,-0.1785},
       {0.1504,0.0198,0.0134,-0.0135,0.2837,0.1894,-0.1909},
       {0.1604,0.0226,0.0153,-0.0154,0.303,0.2016,-0.2032},
       {0.1704,0.0257,0.0174,-0.0175,0.3222,0.2137,-0.2154},
       {0.1804,0.0289,0.0195,-0.0196,0.3416,0.2257,-0.2276},
       {0.1905,0.0323,0.0218,-0.0219,0.3609,0.2377,-0.2397},
       {0.2005,0.036,0.0242,-0.0243,0.3803,0.2496,-0.2517},
       {0.2105,0.0398,0.0267,-0.0268,0.3997,0.2614,-0.2637},
       {0.2205,0.0438,0.0293,-0.0295,0.4192,0.2732,-0.2756},
       {0.2306,0.048,0.032,-0.0323,0.4388,0.2848,-0.2874},
       {0.2406,0.0524,0.0349,-0.0351,0.4583,0.2965,-0.2991},
       {0.2506,0.057,0.0378,-0.0381,0.478,0.308,-0.3108},
       {0.2606,0.0618,0.0409,-0.0412,0.4976,0.3194,-0.3224},
       {0.2707,0.0668,0.0441,-0.0445,0.5174,0.3308,-0.3339},
       {0.2807,0.0719,0.0474,-0.0478,0.5371,0.3421,-0.3453},
       {0.2907,0.0773,0.0509,-0.0513,0.5569,0.3533,-0.3566},
       {0.3007,0.0829,0.0544,-0.0549,0.5768,0.3645,-0.3679},
       {0.3107,0.0887,0.0581,-0.0585,0.5967,0.3755,-0.3791},
       {0.3208,0.0947,0.0618,-0.0623,0.6167,0.3865,-0.3901},
       {0.3308,0.1008,0.0657,-0.0663,0.6367,0.3973,-0.4011},
       {0.3408,0.1072,0.0697,-0.0703,0.6567,0.4081,-0.412},
       {0.3508,0.1138,0.0738,-0.0744,0.6769,0.4188,-0.4227},
       {0.3609,0.1206,0.078,-0.0786,0.697,0.4293,-0.4334},
       {0.3709,0.1276,0.0823,-0.083,0.7172,0.4398,-0.444},
       {0.3809,0.1348,0.0867,-0.0874,0.7375,0.4502,-0.4544},
       {0.3909,0.1422,0.0912,-0.092,0.7578,0.4605,-0.4648},
       {0.401,0.1498,0.0958,-0.0967,0.7782,0.4707,-0.475},
       {0.411,0.1576,0.1005,-0.1014,0.7987,0.4807,-0.4852},
       {0.421,0.1656,0.1054,-0.1063,0.8192,0.4907,-0.4952},
       {0.431,0.1738,0.1103,-0.1112,0.8397,0.5005,-0.5051},
       {0.4411,0.1822,0.1153,-0.1163,0.8603,0.5102,-0.5148},
       {0.4511,0.1908,0.1204,-0.1215,0.881,0.5199,-0.5244},
       {0.4611,0.1997,0.1256,-0.1267,0.9017,0.5293,-0.534},
       {0.4711,0.2087,0.1309,-0.1321,0.9225,0.5387,-0.5433},
       {0.4812,0.2179,0.1363,-0.1375,0.9433,0.5479,-0.5526},
       {0.4912,0.2274,0.1418,-0.1431,0.9642,0.557,-0.5616},
       {0.5012,0.2371,0.1474,-0.1487,0.9852,0.566,-0.5706},
       {0.5112,0.2469,0.1531,-0.1544,1.0062,0.5748,-0.5794},
       {0.5213,0.257,0.1588,-0.1602,1.0273,0.5835,-0.588},
       {0.5313,0.2673,0.1647,-0.1661,1.0484,0.592,-0.5965},
       {0.5413,0.2778,0.1706,-0.1721,1.0696,0.6004,-0.6048},
       {0.5513,0.2886,0.1766,-0.1782,1.0908,0.6087,-0.613},
       {0.5614,0.2995,0.1827,-0.1843,1.1122,0.6167,-0.621},
       {0.5714,0.3106,0.1889,-0.1905,1.1336,0.6247,-0.6288},
       {0.5814,0.322,0.1952,-0.1968,1.155,0.6324,-0.6365},
       {0.5914,0.3336,0.2015,-0.2032,1.1765,0.64,-0.6439},
       {0.6014,0.3454,0.2079,-0.2097,1.1981,0.6474,-0.6512},
       {0.6115,0.3574,0.2144,-0.2162,1.2197,0.6546,-0.6583},
       {0.6215,0.3696,0.221,-0.2228,1.2414,0.6617,-0.6652},
       {0.6315,0.3821,0.2276,-0.2295,1.2632,0.6685,-0.6718},
       {0.6415,0.3947,0.2343,-0.2362,1.285,0.6752,-0.6783},
       {0.6516,0.4076,0.2411,-0.243,1.3069,0.6817,-0.6846},
       {0.6616,0.4207,0.2479,-0.2499,1.3288,0.6879,-0.6906},
       {0.6716,0.434,0.2548,-0.2568,1.3508,0.694,-0.6964},
       {0.6816,0.4476,0.2618,-0.2638,1.3729,0.6998,-0.7019},
       {0.6917,0.4613,0.2688,-0.2708,1.395,0.7054,-0.7073},
       {0.7017,0.4753,0.2759,-0.2779,1.4172,0.7108,-0.7124},
       {0.7117,0.4895,0.283,-0.285,1.4394,0.7159,-0.7172},
       {0.7217,0.5039,0.2902,-0.2922,1.4617,0.7208,-0.7217},
       {0.7318,0.5186,0.2974,-0.2995,1.4841,0.7254,-0.726},
       {0.7418,0.5335,0.3047,-0.3067,1.5065,0.7298,-0.73},
       {0.7518,0.5486,0.312,-0.314,1.5289,0.734,-0.7338},
       {0.7618,0.5639,0.3193,-0.3214,1.5515,0.7378,-0.7372},
       {0.7719,0.5794,0.3267,-0.3288,1.574,0.7414,-0.7403},
       {0.7819,0.5952,0.3342,-0.3362,1.5966,0.7447,-0.7431},
       {0.7919,0.6112,0.3416,-0.3437,1.6192,0.7477,-0.7456},
       {0.8019,0.6275,0.3491,-0.3511,1.6419,0.7504,-0.7478},
       {0.812,0.6439,0.3566,-0.3586,1.6646,0.7527,-0.7496},
       {0.822,0.6606,0.3642,-0.3661,1.6874,0.7548,-0.7511},
       {0.832,0.6775,0.3718,-0.3737,1.7101,0.7565,-0.7522},
       {0.842,0.6947,0.3793,-0.3812,1.7329,0.7579,-0.7529},
       {0.8521,0.712,0.3869,-0.3888,1.7558,0.759,-0.7533},
       {0.8621,0.7296,0.3945,-0.3963,1.7786,0.7596,-0.7532},
       {0.8721,0.7475,0.4022,-0.4039,1.8014,0.76,-0.7528},
       {0.8821,0.7655,0.4098,-0.4114,1.8243,0.7599,-0.752},
       {0.8921,0.7838,0.4174,-0.4189,1.8471,0.7595,-0.7507},
       {0.9022,0.8023,0.425,-0.4265,1.8699,0.7587,-0.749},
       {0.9122,0.8211,0.4326,-0.434,1.8927,0.7574,-0.7468},
       {0.9222,0.84,0.4402,-0.4415,1.9155,0.7558,-0.7442},
       {0.9322,0.8592,0.4478,-0.4489,1.9382,0.7538,-0.7411},
       {0.9423,0.8787,0.4553,-0.4564,1.9609,0.7513,-0.7375},
       {0.9523,0.8983,0.4629,-0.4637,1.9836,0.7484,-0.7335},
       {0.9623,0.9182,0.4704,-0.4711,2.0062,0.745,-0.7289},
       {0.9723,0.9383,0.4778,-0.4784,2.0287,0.7412,-0.7238},
       {0.9824,0.9587,0.4853,-0.4857,2.0511,0.7369,-0.7182},
       {0.9924,0.9792,0.4927,-0.4929,2.0734,0.7322,-0.7121},
       {1.0024,1.0,0.5,-0.5,2.0956,0.727,-0.7054},
       {1.0204,1.0378,0.5131,-0.5127,2.1355,0.7174,-0.6932},
       {1.0384,1.0762,0.526,-0.5252,2.1753,0.7076,-0.6807},
       {1.0565,1.1154,0.5388,-0.5375,2.215,0.6975,-0.6679},
       {1.0745,1.1553,0.5513,-0.5495,2.2547,0.6872,-0.6549},
       {1.0925,1.1959,0.5637,-0.5613,2.2943,0.6766,-0.6415},
       {1.1105,1.2373,0.5759,-0.5728,2.3338,0.6656,-0.6277},
       {1.1285,1.2793,0.5879,-0.5842,2.3732,0.6543,-0.6136},
       {1.1465,1.3221,0.5997,-0.5952,2.4124,0.6426,-0.599},
       {1.1645,1.3655,0.6113,-0.606,2.4516,0.6304,-0.584},
       {1.1826,1.4097,0.6226,-0.6165,2.4905,0.6178,-0.5683},
       {1.2006,1.4545,0.6337,-0.6268,2.5293,0.6046,-0.5521},
       {1.2186,1.5001,0.6446,-0.6367,2.5679,0.5908,-0.5351},
       {1.2366,1.5464,0.6553,-0.6463,2.6062,0.5763,-0.5174},
       {1.2546,1.5933,0.6657,-0.6557,2.6441,0.561,-0.4987},
       {1.2726,1.641,0.6758,-0.6646,2.6817,0.5447,-0.4788},
       {1.2906,1.6893,0.6856,-0.6733,2.7188,0.5274,-0.4577},
       {1.3087,1.7382,0.6951,-0.6815,2.7552,0.5087,-0.4351},
       {1.3267,1.7879,0.7042,-0.6894,2.7907,0.4886,-0.4106},
       {1.3447,1.8381,0.713,-0.6968,2.8251,0.4666,-0.3839},
       {1.3627,1.889,0.7214,-0.7037,2.8579,0.4424,-0.3545},
       {1.3807,1.9405,0.7294,-0.7101,2.8885,0.4155,-0.3219},
       {1.3987,1.9926,0.7369,-0.7159,2.9159,0.3854,-0.2856},
       {1.4168,2.0451,0.7438,-0.721,2.9385,0.3517,-0.2458},
       {1.4348,2.098,0.7502,-0.7254,2.9541,0.3144,-0.2037},
       {1.4528,2.1512,0.7558,-0.7291,2.9602,0.2744,-0.1622},
       {1.4708,2.2046,0.7608,-0.732,2.955,0.2342,-0.1233},
       {1.4888,2.2578,0.765,-0.7342,2.939,0.1969,-0.0885},
       {1.5068,2.3107,0.7686,-0.7358,2.9147,0.1642,-0.0587},
       {1.5248,2.3633,0.7715,-0.7369,2.8847,0.1364,-0.034},
       {1.5429,2.4152,0.774,-0.7375,2.8512,0.113,-0.0134},
       {1.5609,2.4666,0.776,-0.7377,2.8154,0.0932,0.004},
       {1.5789,2.5173,0.7777,-0.7377,2.7781,0.0763,0.0188},
       {1.5969,2.5674,0.7791,-0.7373,2.7398,0.0618,0.0314},
       {1.6149,2.6167,0.7802,-0.7368,2.7008,0.0491,0.0423},
       {1.6329,2.6654,0.7811,-0.736,2.6613,0.0381,0.0518},
       {1.6509,2.7133,0.7817,-0.7351,2.6214,0.0284,0.06},
       {1.669,2.7605,0.7823,-0.734,2.5813,0.0198,0.0672},
       {1.687,2.807,0.7826,-0.7328,2.541,0.0122,0.0735},
       {1.705,2.8528,0.7828,-0.7315,2.5005,0.0054,0.0789},
       {1.723,2.8978,0.7829,-0.73,2.4599,-0.0007,0.0837},
       {1.741,2.9422,0.7829,-0.7285,2.4192,-0.0061,0.0879},
       {1.759,2.9857,0.7828,-0.7269,2.3785,-0.011,0.0915},
       {1.777,3.0286,0.7826,-0.7253,2.3377,-0.0154,0.0946},
       {1.7951,3.0707,0.7823,-0.7236,2.2968,-0.0194,0.0973},
       {1.8131,3.1121,0.782,-0.7218,2.256,-0.0229,0.0996},
       {1.8311,3.1527,0.7816,-0.72,2.215,-0.0261,0.1015},
       {1.8491,3.1926,0.7811,-0.7182,2.1741,-0.0289,0.1031},
       {1.8671,3.2318,0.7806,-0.7164,2.1331,-0.0315,0.1044},
       {1.8851,3.2702,0.78,-0.7145,2.0921,-0.0338,0.1054},
       {1.9031,3.3079,0.7794,-0.7126,2.0512,-0.0358,0.1062},
       {1.9212,3.3449,0.7788,-0.7107,2.0101,-0.0376,0.1067},
       {1.9392,3.3811,0.7781,-0.7087,1.9691,-0.0392,0.107},
       {1.9572,3.4165,0.7774,-0.7068,1.9281,-0.0406,0.1071},
       {1.9752,3.4513,0.7766,-0.7049,1.8871,-0.0418,0.107},
       {1.9932,3.4853,0.7759,-0.703,1.8461,-0.0428,0.1067},
       {2.0112,3.5185,0.7751,-0.701,1.805,-0.0437,0.1063},
       {2.0293,3.551,0.7743,-0.6991,1.764,-0.0444,0.1057},
       {2.0473,3.5828,0.7735,-0.6972,1.7229,-0.0449,0.1049},
       {2.0653,3.6139,0.7727,-0.6953,1.6819,-0.0454,0.104},
       {2.0833,3.6442,0.7719,-0.6934,1.6409,-0.0457,0.103},
       {2.1013,3.6737,0.7711,-0.6916,1.5998,-0.0459,0.1018},
       {2.1193,3.7025,0.7703,-0.6898,1.5588,-0.0459,0.1006},
       {2.1373,3.7306,0.7694,-0.6879,1.5177,-0.0459,0.0992},
       {2.1554,3.758,0.7686,-0.6862,1.4767,-0.0458,0.0977},
       {2.1734,3.7846,0.7678,-0.6844,1.4356,-0.0456,0.0961},
       {2.1914,3.8104,0.767,-0.6827,1.3946,-0.0452,0.0944},
       {2.2094,3.8356,0.7661,-0.681,1.3536,-0.0448,0.0926},
       {2.2274,3.8599,0.7653,-0.6793,1.3125,-0.0443,0.0907},
       {2.2454,3.8836,0.7645,-0.6777,1.2715,-0.0438,0.0888},
       {2.2634,3.9065,0.7637,-0.6761,1.2304,-0.0431,0.0868},
       {2.2815,3.9287,0.763,-0.6745,1.1894,-0.0424,0.0846},
       {2.2995,3.9501,0.7622,-0.673,1.1484,-0.0416,0.0825},
       {2.3175,3.9708,0.7615,-0.6715,1.1073,-0.0408,0.0802},
       {2.3355,3.9907,0.7607,-0.67,1.0663,-0.0399,0.0779},
       {2.3535,4.0099,0.76,-0.6686,1.0253,-0.0389,0.0755},
       {2.3715,4.0284,0.7593,-0.6673,0.9842,-0.0379,0.073},
       {2.3895,4.0461,0.7586,-0.666,0.9432,-0.0368,0.0705},
       {2.4076,4.0631,0.758,-0.6647,0.9022,-0.0356,0.0679},
       {2.4256,4.0794,0.7573,-0.6635,0.8612,-0.0344,0.0653},
       {2.4436,4.0949,0.7567,-0.6623,0.8201,-0.0332,0.0626},
       {2.4616,4.1097,0.7561,-0.6612,0.7791,-0.0319,0.0599},
       {2.4796,4.1237,0.7555,-0.6601,0.7381,-0.0305,0.0571},
       {2.4976,4.137,0.755,-0.6591,0.6971,-0.0292,0.0543},
       {2.5156,4.1495,0.7544,-0.6581,0.6561,-0.0277,0.0514},
       {2.5337,4.1614,0.7539,-0.6572,0.615,-0.0263,0.0485},
       {2.5517,4.1724,0.7535,-0.6563,0.574,-0.0248,0.0455},
       {2.5697,4.1828,0.753,-0.6555,0.533,-0.0232,0.0425},
       {2.5877,4.1924,0.7526,-0.6547,0.492,-0.0216,0.0394},
       {2.6057,4.2013,0.7522,-0.654,0.451,-0.02,0.0363},
       {2.6237,4.2094,0.7519,-0.6533,0.41,-0.0183,0.0332},
       {2.6418,4.2168,0.7515,-0.6527,0.369,-0.0166,0.03},
       {2.6598,4.2234,0.7512,-0.6522,0.328,-0.0149,0.0268},
       {2.6778,4.2293,0.751,-0.6517,0.287,-0.0132,0.0236},
       {2.6958,4.2345,0.7507,-0.6513,0.246,-0.0114,0.0203},
       {2.7138,4.2389,0.7505,-0.6509,0.205,-0.0095,0.017},
       {2.7318,4.2426,0.7503,-0.6506,0.164,-0.0077,0.0137},
       {2.7498,4.2456,0.7502,-0.6504,0.123,-0.0058,0.0103},
       {2.7679,4.2478,0.7501,-0.6502,0.082,-0.0039,0.0069},
       {2.7859,4.2493,0.75,-0.6501,0.041,-0.002,0.0035},
       {2.8039,4.25,0.75,-0.65,0.0,0.0,0.0},
   };
   public SwerveTrajectory getPath() {
       return new SwerveTrajectory(points);
   }
}