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
package org.be.kuleuven.hci.openbadges.utils;

import java.security.SecureRandom;
import java.math.BigInteger;

public final class KeyGenerator
{

  private SecureRandom random = new SecureRandom();

  public String nextKeyId()
  {
    return new BigInteger(130, random).toString(32);
  }
  
  public static void main(String[] args) {
	  KeyGenerator k = new KeyGenerator();
	  System.out.println(k.nextKeyId());
  }

}
