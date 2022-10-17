FROM python:3.8

WORKDIR /usr/src/ai

COPY . .

RUN pip install --upgrade pip
RUN pip install -r requirement.txt

EXPOSE 8001

CMD ["python", "tagAI.py", "--noreload"]