module.exports = {
  roots: ['<rootDir>/simple-todo-frontend/src'], // Ensure Jest is looking at the src folder
  transform: {
    '^.+\\.js$': 'babel-jest', // Use babel-jest to transform JS files
  },
  testEnvironment: 'jsdom', // Use jsdom for testing React components
  transformIgnorePatterns: [
    '/node_modules/(?!axios)/', // Transform axios
  ],
  moduleNameMapper: {
    '^axios$': require.resolve('axios'), // Ensure axios is properly resolved
  },
};
