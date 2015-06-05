#WeSPOT Badges

Two components are part of the weSPOT Badges system (fully described in [D3.3](wespot.net/en/public-deliverables)):

* [Open Badge API] (/OpenBadgesAPI)
* [WeSPOT rules] (/weSPOTOpenBadgesv2)

Both components are deployable in [Google APP Engine](https://appengine.google.com/).

 1. Open Badge API

Open Badge API is a set of REST services that can be used to store badges following the Open Badges specification. The services are described [here](http://wespot.net/apis/-/asset_publisher/84uK/wiki/id/21038428?redirect=http%3A%2F%2Fwespot.net%2Fapis%3Fp_p_id%3D101_INSTANCE_84uK%26p_p_lifecycle%3D0%26p_p_state%3Dnormal%26p_p_mode%3Dview%26p_p_col_id%3Dcolumn-2%26p_p_col_count%3D1).

Description per package:
  - [OpenBadgesAPI/src/org/be/kuleuven/hci/openbadges/] (/OpenBadgesAPI/src/org/be/kuleuven/hci/openbadges/): Contains servlets. These servlets are used [jsp files](/war/) that contain forms. These JSP files are developed specially for weSPOT and facilitate the creation, award and copy of manual badges between inquiries.   
  - [OpenBadgesAPI/src/org/be/kuleuven/hci/openbadges/badgestore/] (/OpenBadgesAPI/src/org/be/kuleuven/hci/openbadges/badgestore/): Contains servlets to enable the creation of badges and the storage of the image in the data store. It uses the [BlobstoreService](https://cloud.google.com/appengine/docs/java/javadoc/com/google/appengine/api/blobstore/BlobstoreService) to store the image in the database. 
  - [OpenBadgesAPI/src/org/be/kuleuven/hci/openbadges/mailnotification] (/OpenBadgesAPI/src/org/be/kuleuven/hci/openbadges/mailnotification) contains classes to send emails as notifications. 
  - [OpenBadgesAPI/src/org/be/kuleuven/hci/openbadges/model] (/OpenBadgesAPI/src/org/be/kuleuven/hci/openbadges/model) contains four classes. An AuthorizedKey is created per consumer/provider of badges. The three other classes correspond to the Open Badge specification. Issuer is the part that defines the institution that issues the badge. Badge contains the definition of the badge itself. AwardedBadge contains the information of the user who is awared with the Badge. 
  - [OpenBadgesAPI/src/org/be/kuleuven/hci/openbadges/persistanceLayer] (/OpenBadgesAPI/src/org/be/kuleuven/hci/openbadges/persistanceLayer): contains the classes to make our model persistant.
  - [OpenBadgesAPI/src/org/be/kuleuven/hci/openbadges/services] (/OpenBadgesAPI/src/org/be/kuleuven/hci/openbadges/services): implements the REST services described [earlier](http://wespot.net/apis/-/asset_publisher/84uK/wiki/id/21038428?redirect=http%3A%2F%2Fwespot.net%2Fapis%3Fp_p_id%3D101_INSTANCE_84uK%26p_p_lifecycle%3D0%26p_p_state%3Dnormal%26p_p_mode%3Dview%26p_p_col_id%3Dcolumn-2%26p_p_col_count%3D1). 
  - [OpenBadgesAPI/src/org/be/kuleuven/hci/openbadges/utils] (/OpenBadgesAPI/src/org/be/kuleuven/hci/openbadges/utils): Miscellaneous package that contains classes to manipulate JSON or that implements a HTTP Client. 

2. WeSPOT rules

weSPOT Rules is a weSPOT specific component. It contains the rules described in [D3.3](wespot.net/en/public-deliverables). 

Description per package:
 - [weSPOTOpenBadgesv2/src/org/be/kuleuven/hci/openbadges/awardingbadges/rules/](/weSPOTOpenBadgesv2/src/org/be/kuleuven/hci/openbadges/awardingbadges/rules/): Contains the rules per phases and the whole inquiry.
 - [weSPOTOpenBadgesv2/src/org/be/kuleuven/hci/openbadges/awardingbadges/model](/weSPOTOpenBadgesv2/src/org/be/kuleuven/hci/openbadges/awardingbadges/model/): contains the model (the same than for our earlier component).
 - [weSPOTOpenBadgesv2/src/org/be/kuleuven/hci/openbadges/awardingbadges/badges](/weSPOTOpenBadgesv2/src/org/be/kuleuven/hci/openbadges/awardingbadges/badges/): contains the classes to create all the badge of the inquiry.
 - [weSPOTOpenBadgesv2/src/org/be/kuleuven/hci/openbadges/awardingbadges/mailnotification](/weSPOTOpenBadgesv2/src/org/be/kuleuven/hci/openbadges/awardingbadges/mailnotification/): Contains the classes to send emails as notifications. For example, every time that a badge is awarded the user will get an email.
 - [weSPOTOpenBadgesv2/src/org/be/kuleuven/hci/openbadges/awardingbadges/services](/weSPOTOpenBadgesv2/src/org/be/kuleuven/hci/openbadges/awardingbadges/services): contains the REST services to create all the different badges. This service is called, in weSPOT, from our Inquiry Workflow Engine (more information in [D3.3](wespot.net/en/public-deliverables)). 
 - [weSPOTOpenBadgesv2/src/org/be/kuleuven/hci/openbadges/awardingbadges/utils](/weSPOTOpenBadgesv2/src/org/be/kuleuven/hci/openbadges/awardingbadges/utils): Miscellaneous package that contains classes to manipulate JSON or that implements a HTTP Client. 
 - [weSPOTOpenBadgesv2/src/org/be/kuleuven/hci/openbadges/awardingbadges/](/weSPOTOpenBadgesv2/src/org/be/kuleuven/hci/openbadges/awardingbadges/): Contains the servlets (cron jobs described in the [cron.xml](https://github.com/weSPOT/wespot_badges/blob/master/weSPOTOpenBadgesv2/war/WEB-INF/cron.xml) file) that evaluate the rules per phase and inquiry.
