/*
 * Copyright 2008-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package griffon.transform

import spock.lang.Specification

import java.lang.reflect.Method

class VetoableSpec extends Specification {
    def 'VetoableASTTransformation is applied to a bean via @Vetoable'() {
        given:
            GroovyShell shell = new GroovyShell()

        when:
            def bean = shell.evaluate('''
            @griffon.transform.Vetoable
            class Bean { }
            new Bean()
            ''')

        then:
            bean instanceof griffon.core.Vetoable
            griffon.core.Vetoable.methods.every { Method target ->
                bean.class.declaredMethods.find { Method candidate ->
                    candidate.name == target.name &&
                    candidate.returnType == target.returnType &&
                    candidate.parameterTypes == target.parameterTypes &&
                    candidate.exceptionTypes == target.exceptionTypes
            }
        }
    }
}