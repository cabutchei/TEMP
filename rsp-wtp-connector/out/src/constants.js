"use strict";
/*-----------------------------------------------------------------------------------------------
 *  Copyright (c) Red Hat, Inc. All rights reserved.
 *  Licensed under the EPL v2.0 License. See LICENSE file in the project root for license information.
 *-----------------------------------------------------------------------------------------------*/
Object.defineProperty(exports, "__esModule", { value: true });
exports.OPTIONS = exports.getImageFilenameForServerType = void 0;
const path = require("path");
const vscode_1 = require("vscode");
/**
 * RSP Provider ID
 */
const RSP_PROVIDER_ID = 'cabutchei.wtp-rsp-server-connector';
/**
 * RSP Provider Name - it will be displayed in the tree node
 */
const RSP_PROVIDER_NAME = 'RSP-WTP Server Connectors';
/**
 * The provider id to be used in the .rsp folder
 */
const RSP_ID = 'cabutchei-wtp-rsp-server-connector';
/**
 * The minimum port for this rsp instance to avoid clobbering
 */
const RSP_MIN_PORT = 8500;
/**
 * The maximum port for this rsp instance to avoid clobbering
 */
const RSP_MAX_PORT = 8999;
/**
 * How long to wait before trying to connect
 */
const RSP_CONNECTION_DELAY = 1500;
/**
 * How frequently to attempt to connect after launch
 */
const RSP_CONNECTION_POLL_INTERVAL = 500;
const getImageFilenameForServerType = (serverType) => {
    if (serverType.startsWith('org.jboss.ide.eclipse.as.7')) {
        return 'jbossas7_ligature.svg';
    }
    else if (serverType.startsWith('org.jboss.ide.eclipse.as.wildfly.')) {
        return 'wildfly_icon.svg';
    }
    else if (serverType.startsWith('org.jboss.ide.eclipse.as.eap.')) {
        return 'jboss.eap.png';
    }
    else if (serverType.startsWith('org.jboss.tools.openshift.cdk.server.type')) {
        return 'Logotype_RH_OpenShift.svg';
    }
    else if (serverType.startsWith('com.ibm.ws.ast.st.v85.server.base')) {
        return 'liberty.png';
    }
    else if (serverType.startsWith('com.ibm.ws.st.server.wlp')) {
        return 'liberty.png';
    }
    else {
        return 'server-light.png';
    }
};
exports.getImageFilenameForServerType = getImageFilenameForServerType;
exports.OPTIONS = {
    providerId: RSP_PROVIDER_ID,
    providerName: RSP_PROVIDER_NAME,
    rspId: RSP_ID,
    minPort: RSP_MIN_PORT,
    maxPort: RSP_MAX_PORT,
    connectionDelay: RSP_CONNECTION_DELAY,
    connectionPollFrequency: RSP_CONNECTION_POLL_INTERVAL,
    minimumSupportedJava: 8,
    getImagePathForServerType: function (serverType) {
        const tmpPath = (0, exports.getImageFilenameForServerType)(serverType);
        if (tmpPath)
            return vscode_1.Uri.file(path.join(__dirname, '..', '..', 'images', tmpPath));
        return null;
    }
};
//# sourceMappingURL=constants.js.map