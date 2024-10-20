FROM node:20

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the Angular app
RUN npm run build --prod

# Expose port 4200
EXPOSE 4200

# Serve the Angular app
CMD ["npx", "ng", "serve", "--host", "0.0.0.0"]
