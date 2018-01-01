/*
 * Copyright 2004 - 2013 Wayne Grant
 *           2013 - 2018 Kai Kramer
 *
 * This file is part of KeyStore Explorer.
 *
 * KeyStore Explorer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * KeyStore Explorer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with KeyStore Explorer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.kse.crypto.secretkey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;
import org.kse.crypto.CryptoException;
import org.kse.crypto.KeyInfo;
import org.kse.crypto.CryptoTestsBase;


public class SecretKeyUtilTest extends CryptoTestsBase {
	@Test
	public void testAllSecretKeyTypes() throws CryptoException {
		for (SecretKeyType secretKeyType : SecretKeyType.values()) {
			testSecretKeyType(secretKeyType);
		}
	}
	@Test
	private void testSecretKeyType(SecretKeyType secretKeyType) throws CryptoException {
		for (int keySize = secretKeyType.minSize(); keySize <= secretKeyType.maxSize(); keySize += secretKeyType
				.stepSize()) {
			SecretKey secretKey = SecretKeyUtil.generateSecretKey(secretKeyType, keySize);

			KeyInfo keyInfo = SecretKeyUtil.getKeyInfo(secretKey);

			assertTrue(secretKeyType.jce().equalsIgnoreCase(keyInfo.getAlgorithm()));
			assertEquals(secretKeyType, SecretKeyType.resolveJce(keyInfo.getAlgorithm()));
			assertEquals(keySize, keyInfo.getSize().intValue());
		}
	}
}
