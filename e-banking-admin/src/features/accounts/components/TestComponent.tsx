"use client"

import { useEffect, useState } from "react"

export default function TestComponent() {

    const [accounts, setAccounts] = useState<string>("")

    useEffect(() => {
        fetch('/account/api/v1/accounts', {
            credentials: 'include'
        })
        .then(res => res.json())
        .then(json => {
            console.log('Json:', json)
            setAccounts(json.data)
        })
    }, [])

    return (
        <>
            <h2>Test Component</h2>
            <h3 style={{color: 'red'}}>{accounts}</h3>
        </>
    )
}