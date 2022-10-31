from secretKey import APP_ID, USER_ID, PAT, MODEL_ID
MODEL_VERSION_ID = ''

############################################################################
# YOU DO NOT NEED TO CHANGE ANYTHING BELOW THIS LINE TO RUN THIS EXAMPLE
############################################################################

from flask import Flask, request;
from flask_cors import CORS;

from clarifai_grpc.channel.clarifai_channel import ClarifaiChannel
from clarifai_grpc.grpc.api import resources_pb2, service_pb2, service_pb2_grpc
from clarifai_grpc.grpc.api.status import status_code_pb2
import warnings
warnings.simplefilter("ignore")

import konlpy
konlpy.__version__

from konlpy.tag import *
komoran = Komoran()

#금지어 지정
exList = ["린지", "이번", "후회", "벌레", "개새끼", "사람", "새끼", "시발", "염병", "베비", "가지"]



app = Flask(__name__);
@app.route('/ai/image', methods = [ 'POST' ])
def shImage(): 
    if request.method == 'POST':
        imgUrl = request.form['url']

        channel = ClarifaiChannel.get_grpc_channel()
        stub = service_pb2_grpc.V2Stub(channel)

        metadata = (('authorization', 'Key ' + PAT),)

        userDataObject = resources_pb2.UserAppIDSet(user_id=USER_ID, app_id=APP_ID)

        post_model_outputs_response = stub.PostModelOutputs(
            service_pb2.PostModelOutputsRequest(
                user_app_id=userDataObject,  # The userDataObject is created in the overview and is required when using a PAT
                model_id=MODEL_ID,
                version_id=MODEL_VERSION_ID,  # This is optional. Defaults to the latest model version
                inputs=[
                    resources_pb2.Input(
                        data=resources_pb2.Data(
                            image=resources_pb2.Image(
                                url=imgUrl
                            )
                        )
                    )
                ],
                # model=resources_pb2.Model(
                #     output_info=resources_pb2.OutputInfo(
                #         output_config=resources_pb2.OutputConfig(
                #             language='ko'
                #         )
                    # )
                # )
            ),
            metadata=metadata
        )
        if post_model_outputs_response.status.code != status_code_pb2.SUCCESS:
            print(post_model_outputs_response.status)
            raise Exception("Post model outputs failed, status: " + post_model_outputs_response.status.description)

        # Since we have one input, one output will exist here
        output = post_model_outputs_response.outputs[0]

        # print("Predicted concepts:")
        tags = []
        for concept in output.data.concepts:
            print("%s %.2f" % (concept.name, concept.value))
            tags.append([concept.name, concept.value ])

        return {'tags' : tags}

@app.route('/ai/image2', methods = [ 'POST' ])
def shImage2(): 
    if request.method == 'POST':
        file_bytes = request.files['file'].read()

        channel = ClarifaiChannel.get_grpc_channel()
        stub = service_pb2_grpc.V2Stub(channel)

        metadata = (('authorization', 'Key ' + PAT),)

        userDataObject = resources_pb2.UserAppIDSet(user_id=USER_ID, app_id=APP_ID)

        post_model_outputs_response = stub.PostModelOutputs(
            service_pb2.PostModelOutputsRequest(
                user_app_id=userDataObject,  # The userDataObject is created in the overview and is required when using a PAT
                model_id=MODEL_ID,
                version_id=MODEL_VERSION_ID,  # This is optional. Defaults to the latest model version
                inputs=[
                    resources_pb2.Input(
                        data=resources_pb2.Data(
                            image=resources_pb2.Image(
                                base64=file_bytes
                            )
                        )
                    )
                ],
                model=resources_pb2.Model(
                    output_info=resources_pb2.OutputInfo(
                        output_config=resources_pb2.OutputConfig(
                            language='ko'
                        )
                    )
                )
            ),
            metadata=metadata
        )
        if post_model_outputs_response.status.code != status_code_pb2.SUCCESS:
            print(post_model_outputs_response.status)
            raise Exception("Post model outputs failed, status: " + post_model_outputs_response.status.description)

        # Since we have one input, one output will exist here
        output = post_model_outputs_response.outputs[0]

        # print("Predicted concepts:")
        tags = []
        for concept in output.data.concepts:
            print("%s %.2f" % (concept.name, concept.value))
            tags.append(concept.name)

        return {'tags' : tags}

@app.route('/ai/sentence', methods = [ 'POST' ])
def strparser():
    if request.method == 'POST':
        # input data
        str = request.json["str"]

        wordList = komoran.nouns("\n".join([s for s in str.split("\n") if s]))
        wordList = [x for x in wordList if len(x)>1 and x not in exList and " " not in x]

        print(set(wordList)) # 이게 정답임
        tags = []
        for word in wordList:
            tags.append(word)
        return {'tags' : tags}

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8081, debug=True)

    # Uncomment this line to print the full Response JSON
    #print(output)