OUTPUT_DIR=build/generated/jaxb
mkdir -p $OUTPUT_DIR
xjc src/main/resources/wsaa-request.xsd -p bill.afip.wsaa.request -d $OUTPUT_DIR
xjc src/main/resources/wsaa-response.xsd -p bill.afip.wsaa.response -d $OUTPUT_DIR