# @tobal/voice-recorder

custom plugin capacitor for recording audio

## Install

```bash
npm install @tobal/voice-recorder
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------

</docgen-api>


Esto con el fin de actualizar el Typescript a la última versión y evitar errores de compilación.
estando en la carpeta raiz del proyecto. 

```bash

rm -rf node_modules package-lock.json
npm install
npm install -D typescript@latest
npx tsc -v

npm run build
