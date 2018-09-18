# test-connect

Basic tool to verify if connection to a specific URL can be established.
Created to troubleshoot customer-side connectivity issues on Sauce Labs' Real Device Cloud.

## Usage

* export APK (or just use the attached app-debug.apk) and upload to your Sauce Labs RDC account
* launch manual testing session on a device
* enter URL to verify connectivity

Exceptions will be printed out in case of failure to expose the underlying issue.
