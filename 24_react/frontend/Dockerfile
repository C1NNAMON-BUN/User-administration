FROM node:13.12.0-alpine

WORKDIR /app
#--silent
COPY ./frontend/package.json ./
#COPY package.json ./
COPY ./frontend/package-lock.json ./
#COPY package-lock.json ./
RUN npm install --silent
RUN npm install react-scripts@3.4.1 -g --silent

# add app
COPY ./frontend/ ./

# start app
CMD ["npm", "start"]