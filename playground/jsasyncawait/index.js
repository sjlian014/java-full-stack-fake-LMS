const doSomethingAsync = () => {
  return new Promise(resolve => {
    setTimeout(() => resolve('I did something'), 3000)
  })
}

const doSomething = async () => {
  console.log(await doSomethingAsync())
  return ["i did it", "are you proud of me?"]
}

console.log('Before')
doSomething()
console.log(doSomething())
doSomething().then(console.log)
console.log('After')