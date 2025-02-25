/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 * 
 * 追加で作成
 * 
 */

package api.exception;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;

public class ExtendedNotAuthorizedException extends NotAuthorizedException {

	// 401 No Authorized
    public ExtendedNotAuthorizedException(final String message) {
        super(Response.status(Response.Status.UNAUTHORIZED).entity(message).build());
    }
}
