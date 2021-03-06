package Client;

public enum ServerRequest {
    GETNAME,GETCUSTOMERINFOS,LOGIN,SIGNUP,DONE,TRUE,NULL,ERROR,FALSE,HASMANAGER,GETACCOUNTTYPE,SIGNOUT,GETPROFILEPHOTO,GETMANAGERINFO,GETSELLERINFO,GETPRODUCTSPAGE,
    GETUSERHASSCORED,GETPRODUCTOFF,GETPRODUCTREVIEWS,GETPRODUCTSELLERS,POSTSCORE,UPDATEADDPRODUCTTOCART,UPDATEREMOVEPRODUCTTOCART,POSTREVIEW,GETSHOPPINGCART,
    UPDATEINCREASECART,UPDATEDECREASECART,UPDATEREMOVECART,GETOFFSINFO,GETALLOFFS,GETOFFSPRODUCTS,GETSELLERPRODUCTS,POSTREMOVEPRODUCTREQ,GETSELLERGOTPRODUCT,
    POSTADDPRODUCTTOSELLERLIST,POSTEDITPRODUCTREQ,POSTCREATEPRODUCTREQ,GETPRODUCT,GETBUYERSPRODUCT,GETBUYLOGS,GETBUYLOGPRODUCTS,GETSELLLOGS,GETSELLLOGPRODUCTS,
    GETSELLEROFFS,GETSELLERGOTOFF,GETCUSTOMERDISCOUNTCODES,UPDATEFIELDMANAGER,GETALLCATEGORIES,POSTREMOVECATEGORY,GETCATEGORYEXISTS,POSTCREATECATEGORY,
    UPDATEEDITCATEGORY,GETALLACCOUNTS,GETCHECKUSEREXISTS,POSTREMOVEUSER,POSTCREATEMANAGERPROFILE,POSTCREATECOMPANY,POSTMAKEMANAGERFIRST,POSTCREATEDISCOUNTCODE,
    GETALLDISCOUNTCODES,GETDISCOUNTCODEEXISTS,POSTREMOVEDISCOUNTCODE,UPDATEEDITDISCOUNTCODE,GETALLPRODUCTS,POSTREMOVEPRODUCT,GETALLREQUESTS,POSTACCEPTREQUEST,
    POSTDECLINEREQUEST,GETCHECKREQUESTEXISTS,GETDISCOUNTCODEINFOS,GETREQUESTINFOS,GETUSERINFOS,POSTCREATEOFF,UPDATEEDITFIELDSELLER,UPDATEEDITOFFREQ,POSTPAYMENT,
    GETALLAUCTIONS,GETALLFILES,GETALLSELLERFILES,POSTUPLOADFILE,GETACCOUNT,POSTBUYFILE,GETCUSTOMERFILES,GETFILE,GETSELLERAUCTIONS,POSTCREATEAUCTION,POSTSUGGESTAUCTION,
    GETUPDATEAUCTION,POSTCREATESUPPORT,GETCOMMISIONANDLEASTWALLET,POSTCOMMISSION,POSTLEASTWALLETAMOUNT,GETSELLERSCUSTOMERSBALANCE,POSTDEPOSITMONEY,POSTCHARGEWALLET,
    POSTWITHDRAWWALLET,GETALLBUYLOGS,UPDATEDELIVERLOG
}
