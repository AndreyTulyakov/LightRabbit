precision mediump float;
uniform float time;
uniform vec2 resolution;


float noise2d(vec2 p) {
	return fract(sin(dot(p.xy ,vec2(12.9898,78.233))) * 456367.5453);
}


void main( void ) {
	
	vec2 p = ( gl_FragCoord.xy / resolution.xy );
	
	float a = 0.5;
	for (int i = 1; i < 5; i++) {
		float fi = float(i);
		float s = floor(200.0*(p.x)/fi + 50.0*fi + time*3.0);
		
		if (p.y < noise2d(vec2(s))*fi/25.0 - fi*.1 + 1.0) {
			a = float(i)/30.;
		}
	}
	vec3 mcolor = vec3(1.1, 1.1, 1.3);
	gl_FragColor = vec4(vec3(a)*mcolor, 1.0 );
}