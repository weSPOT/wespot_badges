package org.be.kuleuven.hci.openbadges.persistanceLayer;

import java.util.Hashtable;

import org.be.kuleuven.hci.openbadges.model.AuthorizedKey;
import org.be.kuleuven.hci.openbadges.model.AwardedBadge;
import org.be.kuleuven.hci.openbadges.model.AwardedBadges;
import org.be.kuleuven.hci.openbadges.model.Badge;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {
	
	private static OfyService _ofyService;
    
	OfyService (){

        factory().register(AwardedBadges.class);
        factory().register(AuthorizedKey.class);

    }
	
	public static synchronized OfyService getOfyService() {
		if (_ofyService == null) {
			_ofyService = new OfyService();
			
		}
		return _ofyService;
	}

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
    
}