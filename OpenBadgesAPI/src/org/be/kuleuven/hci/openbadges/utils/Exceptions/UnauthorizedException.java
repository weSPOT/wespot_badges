/*******************************************************************************
 * Copyright (c) 2014 KU Leuven
 * This library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this library.  If not, see <http://www.gnu.org/licenses/>.
 *  
 * Contributors:
 *     Jose Luis Santos
 *******************************************************************************/
package org.be.kuleuven.hci.openbadges.utils.Exceptions;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;
 
/** Throw this exception to return a 401 Unauthorized response.  The WWW-Authenticate header is
 * set appropriately and a short message is included in the response entity. */
public class UnauthorizedException extends WebApplicationException
{
    private static final long serialVersionUID = 1L;
 
    public UnauthorizedException()
    {
        this("Please authenticate.", "BadgeAPI");
    }
 
    public UnauthorizedException(String message, String realm)
    {
        super(Response.status(Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE,
                                                          "Basic realm=\"" + realm + "\"")
                .entity(message).build());
    }
}