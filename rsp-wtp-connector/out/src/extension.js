"use strict";
/*-----------------------------------------------------------------------------------------------
 *  Copyright (c) Red Hat, Inc. All rights reserved.
 *  Licensed under the EPL v2.0 License. See LICENSE file in the project root for license information.
 *-----------------------------------------------------------------------------------------------*/
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.activate = activate;
exports.deactivate = deactivate;
const constants_1 = require("./constants");
const extensionImpl_1 = require("./impl/extensionImpl");
// this method is called when your extension is activated
function activate(context) {
    return __awaiter(this, void 0, void 0, function* () {
        return (0, extensionImpl_1.activateImpl)(context, constants_1.OPTIONS);
    });
}
// this method is called when your extension is deactivated
function deactivate() {
    return __awaiter(this, void 0, void 0, function* () {
        return (0, extensionImpl_1.deactivateImpl)(constants_1.OPTIONS);
    });
}
//# sourceMappingURL=extension.js.map