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

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;

public class ExtendedBadRequestException extends BadRequestException {

	// 400 Bad Request
    public ExtendedBadRequestException(final String message) {
        super(Response.status(Response.Status.BAD_REQUEST).entity(message).build());
    }
}
