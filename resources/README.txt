Agregar el certificado al keystore del JDK:

1. cd %JAVA_HOME%/jre/lib/security
2. keytool -import -noprompt -trustcacerts -alias storage-rackspace -file $PATH_CERTIFICATE/cer-storage.crt -keystore cacerts -storepass changeit