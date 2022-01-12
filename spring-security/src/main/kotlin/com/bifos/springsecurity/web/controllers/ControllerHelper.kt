package com.bifos.springsecurity.web.controllers

/**
 * @author Mick Knutson
 */
class ControllerHelper {

    companion object {

        /**
         * Redirect helper
         * Usage:
         *      ControllerHelper.redirect() -> "/"
         *
         * Result:
         *      "redirect:/"
         *
         * @param path for URI
         * @return Redirect URI path
         */
        fun redirect(path: String) = "redirect:${path}"
    }

}